package com.tiger.ar.museum.presentation.collection

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.databinding.CollectionItemBinding
import com.tiger.ar.museum.domain.model.MCollection
import com.tiger.ar.museum.presentation.widget.BaseGridAdapter

class CollectionAdapter: BaseGridAdapter() {
    override fun getLayoutResource(viewType: Int) = R.layout.collection_item

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return CollectionVH(binding as CollectionItemBinding)
    }

    override fun getItemCountInRow(viewType: Int) = 2

    inner class CollectionVH(private val binding: CollectionItemBinding) : BaseVH<MCollection>(binding) {

        init {

        }

        override fun onBind(data: MCollection) {
            binding.apply {
                ivCollectionThumbnail.loadImage(data.thumbnail)
                tvCollectionTitle.text = data.name
            }
        }
    }

}
