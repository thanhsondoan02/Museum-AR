package com.tiger.ar.museum.presentation.collection.all

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.GridMuseumAdapter
import com.tiger.ar.museum.databinding.CollectionsItemBinding
import com.tiger.ar.museum.domain.model.MCollection

open class CollectionsAdapter : GridMuseumAdapter() {
    var listener: IListener? = null

    override fun setupEmptyState() = Empty(overrideLayoutRes = R.layout.collections_empty_item)

    override fun getItemCountInRow(viewType: Int) = 2

    override fun getLayoutResource(viewType: Int) = R.layout.collections_item

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return CollectionVH(binding as CollectionsItemBinding)
    }

    inner class CollectionVH(val binding: CollectionsItemBinding) : BaseVH<MCollection>(binding) {
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

    interface IListener {
        fun onCollectionClick(collection: MCollection)
    }
}
