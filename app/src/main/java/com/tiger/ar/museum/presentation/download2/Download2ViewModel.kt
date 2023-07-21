package com.tiger.ar.museum.presentation.download2

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.MimeTypeMap
import androidx.lifecycle.viewModelScope
import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.common.extension.failure
import com.tiger.ar.museum.common.extension.getApplication
import com.tiger.ar.museum.common.extension.success
import com.tiger.ar.museum.common.usecase.FlowResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import java.util.UUID


class Download2ViewModel : BaseViewModel() {
    var data: MutableList<DownloadFile> = mutableListOf()
    var downloadingFile: DownloadFile? = null

    private var _updateListState = MutableStateFlow(FlowResult.newInstance<List<DownloadFile>>())
    val updateListState = _updateListState.asStateFlow()

    private var _startDownloadState = MutableStateFlow(FlowResult.newInstance<DownloadFile>())
    val startDownloadState = _startDownloadState.asStateFlow()

    private val downloadManager = getApplication().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    fun updateProgress(downloadFile: DownloadFile) {
        viewModelScope.launch {
            downloadFile.let {
                it.percent = getDownloadProgress(it.downloadId)
            }
            _updateListState.success(copyAndReverseOfData())
        }
    }

    fun addToQueue(url: String) {
        viewModelScope.launch {
            try {
                URL(url)
            } catch (e: MalformedURLException) {
                _updateListState.failure(IllegalStateException("URL is invalid"))
                return@launch
            }

            data.add(
                DownloadFile(
                    id = data.lastOrNull()?.id?.plus(1) ?: 0,
                    url = url,
                    status = Download2Activity.DOWNLOAD_PENDING,
                    percent = 0
                )
            )
            _updateListState.success(copyAndReverseOfData())
            checkIfCanDownload()
        }
    }

    fun checkIfCanDownload() {
        viewModelScope.launch {
            val downloadingFile = data.find { it.status == Download2Activity.DOWNLOADING }
            if (downloadingFile == null) {
                for (file in data) {
                    if (file.status == Download2Activity.DOWNLOAD_PENDING) {
                        startDownload(file)
                        return@launch
                    }
                }
            }
        }
    }

    fun getDataFromPref() {
        viewModelScope.launch {
            data =
                AppPreferences.getValueJson<DownloadData>(Download2Activity.DOWNLOAD_KEY)?.list?.mapNotNull { it }
                    ?.toMutableList() ?: mutableListOf()
        }
    }

    fun saveDataToPref() {
        viewModelScope.launch {
            AppPreferences.setValueJson(
                Download2Activity.DOWNLOAD_KEY,
                DownloadData(list = data)
            )
        }
    }

    fun setState(downloadId: Long, state: Int) {
        viewModelScope.launch {
            val file = data.find { it.downloadId == downloadId }
            if (file != null) {
                file.status = state
                _updateListState.success(copyAndReverseOfData())
                if (state == Download2Activity.DOWNLOAD_SUCCESS) {
                    checkIfCanDownload()
                }
            }
        }
    }

    fun startDownload(downloadFile: DownloadFile) {
        viewModelScope.launch {
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
            _updateListState.success(copyAndReverseOfData())

            downloadingFile = downloadFile
            _startDownloadState.success(downloadFile)
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
}
