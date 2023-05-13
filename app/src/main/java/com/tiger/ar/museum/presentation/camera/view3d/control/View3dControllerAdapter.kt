package com.tiger.ar.museum.presentation.camera.view3d.control

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.gone
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.extension.show
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.View3dControllerItemBinding
import com.tiger.ar.museum.domain.model.Item
import com.tiger.ar.museum.presentation.camera.view3d.DOWNLOAD_STATUS

class View3dControllerAdapter: MuseumAdapter() {
    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int) = R.layout.view_3d_controller_item

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return ModelVH(binding as View3dControllerItemBinding)
    }

    inner class ModelVH(private val binding: View3dControllerItemBinding) : BaseVH<ItemDisplay>(binding) {
        init {
            binding.mcvView3dControllerThumbnail.setOnSafeClick {
                getItem {
                    it.downloadStatus = DOWNLOAD_STATUS.DOWNLOADING
                    updateDownloadStatus(it)
                    listener?.onDownloadClick(it)
                }
            }
        }

        override fun onBind(data: ItemDisplay) {
            binding.ivView3dControllerThumbnail.loadImage(data.item.thumbnail)
            updateDownloadStatus(data)
        }

        private fun updateDownloadStatus(data: ItemDisplay) {
            when (data.downloadStatus) {
                DOWNLOAD_STATUS.DOWNLOADED -> {
                    binding.ivView3dControllerDownload.gone()
                    binding.flView3dControllerProgress.gone()
                }
                DOWNLOAD_STATUS.DOWNLOADING -> {
                    binding.ivView3dControllerDownload.gone()
                    binding.flView3dControllerProgress.show()
                }
                DOWNLOAD_STATUS.NOT_DOWNLOADED -> {
                    binding.ivView3dControllerDownload.show()
                    binding.flView3dControllerProgress.gone()
                }
            }
        }
    }

    data class ItemDisplay (
        var item: Item,
        var downloadStatus: DOWNLOAD_STATUS = DOWNLOAD_STATUS.NOT_DOWNLOADED
    )

    interface IListener {
        fun onDownloadClick(itemDisplay: ItemDisplay)
    }
}
