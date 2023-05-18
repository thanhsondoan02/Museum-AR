package com.tiger.ar.museum.presentation.search

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.databinding.CollectionsItem2Binding
import com.tiger.ar.museum.domain.model.MCollection
import com.tiger.ar.museum.presentation.collection.all.CollectionsAdapter

class CollectionsAdapter2: CollectionsAdapter() {
    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.collections_item_2
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return CollectionVH(binding as CollectionsItem2Binding)
    }

    inner class CollectionVH(val binding: CollectionsItem2Binding) : BaseVH<MCollection>(binding) {
        init {
            binding.constCollectionsRoot.setOnClickListener {
                getItem { currentItem ->
                    listener?.onCollectionClick(currentItem)
                }
            }
        }

        override fun onBind(data: MCollection) {
            binding.apply {
                binding.ivCollectionsIcon.loadImage(data.icon)
                binding.ivCollectionsThumbnail.loadImage(data.thumbnail)
                binding.tvCollectionsName.text = data.name
                binding.tvCollectionsPlace.text = data.place
            }
        }
    }
}
