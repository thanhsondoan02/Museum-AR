package com.tiger.ar.museum.presentation.camera.view3d.control

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.getAppColor
import com.tiger.ar.museum.common.extension.getAppDimensionPixel
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
    companion object {
        const val SELECT_PAYLOAD = "SELECT_PAYLOAD"
        const val DOWNLOAD_PAYLOAD = "DOWNLOAD_PAYLOAD"
    }

    var listener: IListener? = null

    override fun getDiffUtil(oldList: List<Any>, newList: List<Any>): DiffUtil.Callback {
        return View3dDiffUtil(oldList, newList)
    }

    override fun getLayoutResource(viewType: Int) = R.layout.view_3d_controller_item

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return ModelVH(binding as View3dControllerItemBinding)
    }

    inner class ModelVH(private val binding: View3dControllerItemBinding) : BaseVH<ItemDisplay>(binding) {
        init {
            binding.mcvView3dControllerThumbnail.setOnSafeClick {
                getItem {
                    listener?.onItemClick(it)
                }
            }
        }

        override fun onBind(data: ItemDisplay) {
            binding.ivView3dControllerThumbnail.loadImage(data.item.thumbnail)
            updateDownloadStatus(data)
            updateSelectStatus(data)
        }

        override fun onBind(data: ItemDisplay, payloads: List<Any>) {
            binding.apply {
                (payloads.firstOrNull() as? List<*>)?.forEach {
                    when (it) {
                        SELECT_PAYLOAD -> updateSelectStatus(data)
                        DOWNLOAD_PAYLOAD -> updateDownloadStatus(data)
                    }
                }
            }
        }

        private fun updateSelectStatus(data: ItemDisplay) {
            binding.mcvView3dControllerThumbnail.apply {
                if (data.isSelected) {
                    strokeColor = getAppColor(R.color.green)
                    strokeWidth = getAppDimensionPixel(R.dimen.dimen_2)
                } else {
                    strokeColor = getAppColor(R.color.white)
                    strokeWidth = getAppDimensionPixel(R.dimen.dimen_1)
                }
            }
        }

        private fun updateDownloadStatus(data: ItemDisplay) {
            when (data.downloadStatus) {
                DOWNLOAD_STATUS.DOWNLOADED -> {
                    binding.flView3dControllerDownload.gone()
                    binding.flView3dControllerProgress.gone()
                }
                DOWNLOAD_STATUS.DOWNLOADING -> {
                    binding.flView3dControllerDownload.gone()
                    binding.flView3dControllerProgress.show()
                }
                DOWNLOAD_STATUS.NOT_DOWNLOADED -> {
                    binding.flView3dControllerDownload.show()
                    binding.flView3dControllerProgress.gone()
                }
            }
        }
    }

    data class ItemDisplay (
        var item: Item,
        var downloadStatus: DOWNLOAD_STATUS = DOWNLOAD_STATUS.NOT_DOWNLOADED,
        var isSelected: Boolean = false
    ) {
        fun copy(): ItemDisplay {
            return ItemDisplay(item, downloadStatus, isSelected)
        }
    }

    interface IListener {
        fun onItemClick(itemDisplay: ItemDisplay)
    }
}
