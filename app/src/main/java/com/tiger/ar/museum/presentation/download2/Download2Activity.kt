package com.tiger.ar.museum.presentation.download2

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.activity.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.IViewListener
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.extension.coroutinesLaunch
import com.tiger.ar.museum.common.extension.handleUiState
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
    private var downloadReceiver: BroadcastReceiver? = null
    private var runnable: Runnable? = null

    override fun onDestroy() {
        unregisterReceiver(downloadReceiver)
        super.onDestroy()
    }

    override fun onInitView() {
        super.onInitView()
        initRecycleView()
        initOnClick()
        initReceiver()
    }

    override fun onObserverViewModel() {
        super.onObserverViewModel()
        coroutinesLaunch(viewModel.updateListState) {
            handleUiState(it, object : IViewListener {
                override fun onSuccess() {
                    binding.cvDownload2History.submitList(it.data)
                }

                override fun onFailure() {
                    toast(it.throwable?.message ?: "Failed")
                }
            }, canHideLoading = true, canShowLoading = true)
        }

        coroutinesLaunch(viewModel.startDownloadState) {
            handleUiState(it, object : IViewListener {
                override fun onSuccess() {
                    startRunner(it.data)
                }

                override fun onFailure() {
                    toast(it.throwable?.message ?: "Failed")
                }
            }, canHideLoading = true, canShowLoading = true)
        }
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
            viewModel.addToQueue(binding.edtDownload2Link.text.toString())
        }
    }

    private fun initReceiver() {
        downloadReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent.action) {
                    stopRunner()
                    val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                    if (downloadId != -1L) {
                        viewModel.setState(downloadId, DOWNLOAD_SUCCESS)
                    }
                } else {
                    toast("Download failed")
                }
            }
        }
        registerReceiver(downloadReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun startRunner(downloadFile: DownloadFile?) {
        if (downloadFile == null) return
        runnable = object : Runnable {
            override fun run() {
                viewModel.updateProgress(downloadFile)
                binding.cvDownload2History.postDelayed(this, 1000)
            }
        }
        binding.cvDownload2History.postDelayed(runnable!!, 1000)
    }

    private fun stopRunner() {
        runnable?.let {
            binding.cvDownload2History.removeCallbacks(it)
        }
        runnable = null
    }

}
