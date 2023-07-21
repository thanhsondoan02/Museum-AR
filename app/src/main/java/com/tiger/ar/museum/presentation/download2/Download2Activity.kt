package com.tiger.ar.museum.presentation.download2

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.activity.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.Download2ActivityBinding
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE


class Download2Activity : MuseumActivity<Download2ActivityBinding>(R.layout.download2_activity) {
    companion object {
        const val DOWNLOAD_KEY = "DOWNLOAD_KEY"

        const val DOWNLOAD_PENDING = -2
        const val DOWNLOAD_FAILED = -1
        const val DOWNLOADING = 0
        const val DOWNLOAD_SUCCESS = 1
    }

    private val adapter: Download2Adapter by lazy { Download2Adapter() }
    private val viewModel by viewModels<Download2ViewModel>()

    private lateinit var mService: DownloadService
    private var mBound: Boolean = false
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as DownloadService.LocalBinder
            mService = binder.getService().apply {
                listener = object : DownloadService.IListener {
                    override fun onUpdateListStateSuccess(data: List<DownloadFile>) {
                        binding.cvDownload2History.submitList(data)
                    }

                    override fun onUpdateListStateFailed(message: String) {
                        toast(message)
                    }
                }
            }
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onDestroy() {
        unbindService(connection)
        mBound = false
        super.onDestroy()
    }

    override fun onInitView() {
        super.onInitView()
        Intent(this, DownloadService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
        initRecycleView()
        initOnClick()
    }

    private fun initRecycleView() {
        adapter.listener = object : Download2Adapter.IListener {
            override fun onCancelClick() {
                finish()
            }

            override fun onRetryClick() {
                finish()
            }
        }
        binding.cvDownload2History.apply {
            setAdapter(this@Download2Activity.adapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }
    }

    private fun initOnClick() {
        binding.ivDownload2Start.setOnSafeClick {
            if (mBound) {
                mService.addToQueue(binding.edtDownload2Link.text.toString())
            }
        }
    }

}
