package com.tiger.ar.museum.presentation.camera

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.View3dItemBinding
import com.tiger.ar.museum.domain.model.Model3d

class View3dAdapter: MuseumAdapter() {
    override fun getLayoutResource(viewType: Int) = R.layout.view_3d_item

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return ModelVH(binding as View3dItemBinding)
    }

    inner class ModelVH(private val binding: View3dItemBinding) : BaseVH<Model3d>(binding) {
        init {
            binding.tvView3dDownload.setOnSafeClick {
                getItem {
                }
            }
        }

        override fun onBind(data: Model3d) {
            binding.ivView3d.loadImage(data.thumbnail)
            binding.tvView3dName.text = data.name
        }
    }
}
