package com.tiger.ar.museum.presentation.download2

import android.app.DownloadManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Binder
import android.os.Environment
import android.os.Handler
import android.os.IBinder
import android.webkit.MimeTypeMap
import com.tiger.ar.museum.common.extension.toast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import java.util.UUID

@OptIn(DelicateCoroutinesApi::class)
class DownloadService : Service() {
    var data: MutableList<DownloadFile> = mutableListOf()
    var downloadingFile: DownloadFile? = null
    var listener: IListener? = null

    private val binder = LocalBinder()
    private var downloadReceiver: BroadcastReceiver? = null
    private var runnable: Runnable? = null
    private val downloadManager by lazy { getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }
    private var handler: Handler? = null

    override fun onCreate() {
        super.onCreate()
        initReceiver()
        handler = Handler()
        runnable = object : Runnable {
            override fun run() {
                updateProgress()
                handler?.postDelayed(this, 1000)
            }
        }
    }

    override fun onDestroy() {
        unregisterReceiver(downloadReceiver)
        downloadReceiver = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun addToQueue(url: String) {
        try {
            URL(url)
        } catch (e: MalformedURLException) {
            listener?.onUpdateListStateFailed("Invalid URL")
            return
        }

        data.add(
            DownloadFile(
                id = data.lastOrNull()?.id?.plus(1) ?: 0,
                url = url,
                status = Download2Activity.DOWNLOAD_PENDING,
                percent = 0
            )
        )
        
        GlobalScope.launch {
            listener?.onUpdateListStateSuccess(copyAndReverseOfData())
            checkIfCanDownload()
        }
    }

    private fun checkIfCanDownload() {
        GlobalScope.launch {
            val downloadingFile = data.find { it.status == Download2Activity.DOWNLOADING }
            if (downloadingFile == null) {
                val pendingFile = data.find { it.status == Download2Activity.DOWNLOAD_PENDING }
                if (pendingFile != null) {
                    startDownload(pendingFile)
                }
            }
        }
    }

    private fun setState(downloadId: Long, state: Int) {
        GlobalScope.launch {
            val file = data.find { it.downloadId == downloadId }
            if (file != null) {
                file.status = state
                listener?.onUpdateListStateSuccess(copyAndReverseOfData())
                if (state == Download2Activity.DOWNLOAD_SUCCESS) {
                    checkIfCanDownload()
                }
            }
        }
    }

    private fun updateProgress() {
        GlobalScope.launch {
            downloadingFile?.let {
                it.percent = getDownloadProgress(it.downloadId)
            }
            listener?.onUpdateListStateSuccess(copyAndReverseOfData())
        }
    }

    private fun startDownload(downloadFile: DownloadFile) {
        GlobalScope.launch {
            val url = downloadFile.url
            val downloadDirectory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val originFileName = getFileNameFromURL(url)
            var file = File(downloadDirectory, originFileName)
            var countExist = 0
            while (file.exists()) {
                countExist++
                file = File(downloadDirectory, getExactFileName(originFileName, countExist))
            }
            val exactFileName = getExactFileName(originFileName, countExist)
            val request = DownloadManager.Request(Uri.parse(url))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, exactFileName)
            downloadFile.apply {
                status = Download2Activity.DOWNLOADING
                name = exactFileName
                downloadId = downloadManager.enqueue(request)
            }
            listener?.onUpdateListStateSuccess(copyAndReverseOfData())

            downloadingFile = downloadFile
            startRunner()
        }
    }

    private fun getExactFileName(origin: String, countExist: Int): String {
        if (countExist == 0) {
            return origin
        }
        val dotPosition = origin.lastIndexOf('.')
        return if (dotPosition == -1) {
            "$origin($countExist)"
        } else {
            val name = origin.substring(0, dotPosition)
            val extension = origin.substring(dotPosition)
            "$name($countExist)$extension"
        }
    }

    private fun getFileNameFromURL(url: String?): String {
        val path = URL(url).path
        return if (FilenameUtils.getExtension(path).isEmpty()) {
            if (getMimeType(url) == null) {
                generateRandomFileName()
            } else {
                generateRandomFileName() + "." + getMimeType(url)
            }
        } else {
            FilenameUtils.getName(path)
        }
    }

    private fun getMimeType(url: String?): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    private fun generateRandomFileName(): String {
        return UUID.randomUUID().toString()
    }

    private fun copyAndReverseOfData(): List<DownloadFile> {
        return data.map { it.copy() }.reversed()
    }

    private fun getDownloadProgress(downloadingId: Long? = null): Int {
        if (downloadingId == null || downloadingId == -1L) {
            return -1
        }
        val query = DownloadManager.Query()
        query.setFilterById(downloadingId)
        val cursor = downloadManager.query(query)
        if (cursor != null && cursor.moveToFirst()) {
            val bytesDownloadedSoFarIndex =
                cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
            val bytesTotalIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
            val bytesDownloadedSoFar = cursor.getLong(bytesDownloadedSoFarIndex)
            val bytesTotal = cursor.getLong(bytesTotalIndex)
            cursor.close()
            return (bytesDownloadedSoFar * 100 / bytesTotal).toInt()
        }
        return -1
    }

    private fun initReceiver() {
        downloadReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent.action) {
                    stopRunner()
                    val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                    if (downloadId != -1L) {
                        setState(downloadId, Download2Activity.DOWNLOAD_SUCCESS)
                    }
                } else {
                    toast("Download failed")
                }
            }
        }
        registerReceiver(downloadReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun startRunner() {
        handler?.postDelayed(runnable!!, 1000)
    }

    private fun stopRunner() {
        runnable?.let {
            handler?.removeCallbacks(it)
        }
        downloadingFile = null
    }

    inner class LocalBinder : Binder() {
        fun getService(): DownloadService = this@DownloadService
    }
    
    interface IListener {
        fun onUpdateListStateSuccess(data: List<DownloadFile>)
        fun onUpdateListStateFailed(message: String)
    }
}
