package com.tiger.ar.museum.presentation.download

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.DownloadItemBinding

class DownloadAdapter : MuseumAdapter() {
    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.download_item
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return DownloadVH(binding as DownloadItemBinding)
    }

    inner class DownloadVH(val binding: DownloadItemBinding) : BaseVH<DownloadItem>(binding) {
        init {
            binding.ivDownloadDelete.setOnSafeClick { getItem { listener?.onDelete(it) } }
            binding.root.setOnSafeClick { getItem { listener?.onViewItem(it.id) } }
        }

        override fun onBind(data: DownloadItem) {
            binding.tvDownloadName.text = data.name
            binding.tvDownloadSize.text = data.getSize()
            binding.ivDownloadThumbnail.loadImage(data.thumbnail)
        }
    }

    data class DownloadItem(
        var id: String? = null,
        var name: String? = null,
        var thumbnail: String? = null
    ) {
        fun getSize(): String {
            return "39 MB"
        }
    }

    interface IListener {
        fun onDelete(item: DownloadItem)
        fun onViewItem(id: String?)
    }
}
