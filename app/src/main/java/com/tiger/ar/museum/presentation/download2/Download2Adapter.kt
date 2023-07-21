package com.tiger.ar.museum.presentation.download2

import android.annotation.SuppressLint
import android.view.ViewGroup.MarginLayoutParams
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.getAppDimensionPixel
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.gone
import com.tiger.ar.museum.common.extension.show
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.Download2ItemBinding

class Download2Adapter : MuseumAdapter() {
    companion object {
        const val NAME_PAYLOAD = "NAME_PAYLOAD"
        const val PERCENT_PAYLOAD = "PERCENT_PAYLOAD"
        const val STATUS_PAYLOAD = "STATUS_PAYLOAD"
    }

    var listener: IListener? = null

    @Suppress("UNCHECKED_CAST")
    override fun getDiffUtil(oldList: List<Any>, newList: List<Any>): DiffUtil.Callback {
        return Download2DiffUtil(oldList as List<DownloadFile>, newList as List<DownloadFile>)
    }

    override fun getLayoutResource(viewType: Int) = R.layout.download2_item

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return Download2VH(binding as Download2ItemBinding)
    }

    @SuppressLint("SetTextI18n")
    inner class Download2VH(val binding: Download2ItemBinding) : BaseVH<DownloadFile>(binding) {
        override fun onBind(data: DownloadFile) {
            super.onBind(data)
            binding.tvDownload2Url.text = getAppString(R.string.url) + ": " +  data.url
            updateStatus(data)
            updateName(data)
        }

        override fun onBind(data: DownloadFile, payloads: List<Any>) {
            super.onBind(data, payloads)
            binding.apply {
                (payloads.firstOrNull() as? List<*>)?.forEach {
                    when (it) {
                        NAME_PAYLOAD -> updateName(data)
                        PERCENT_PAYLOAD -> tvDownload2Status.text = getAppString(R.string.status) + ": " + "${data.percent}%"
                        STATUS_PAYLOAD -> updateStatus(data)
                    }
                }
            }
        }

        private fun updateName(data: DownloadFile) {
            with(binding.tvDownload2Name) {
                if (data.status == Download2Activity.DOWNLOAD_SUCCESS || data.status == Download2Activity.DOWNLOADING) {
                    show()
                    text = getAppString(R.string.name) + ": " + data.name
                    (binding.tvDownload2Url.layoutParams as MarginLayoutParams).topMargin = getAppDimensionPixel(R.dimen.dimen_12)
                } else {
                    gone()
                    (binding.tvDownload2Url.layoutParams as MarginLayoutParams).topMargin = 0
                }
            }
        }

        private fun updateStatus(data: DownloadFile) {
            binding.tvDownload2Status.text = getAppString(R.string.status) + ": " + when (data.status) {
                Download2Activity.DOWNLOAD_FAILED -> getAppString(R.string.download_failed)
                Download2Activity.DOWNLOAD_PENDING -> getAppString(R.string.download_pending)
                Download2Activity.DOWNLOADING -> "${data.percent}%"
                Download2Activity.DOWNLOAD_SUCCESS -> getAppString(R.string.download_success)
                else -> getAppString(R.string.download_unknown)
            }
        }
    }

    interface IListener {
        fun onCancelClick()
        fun onRetryClick()
    }
}
