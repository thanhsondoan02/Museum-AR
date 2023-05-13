package com.tiger.ar.museum.presentation.favorite

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.FavoriteCollectionChildItemBinding
import com.tiger.ar.museum.domain.model.MCollection

class FavoriteCollectionAdapter : MuseumAdapter() {
    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int) = R.layout.favorite_collection_child_item

    override fun setupEmptyState() = Empty(overrideLayoutRes = R.layout.empty_favorite_collections_item)

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return FavoriteCollectionVH(binding as FavoriteCollectionChildItemBinding)
    }

    inner class FavoriteCollectionVH(private val binding: FavoriteCollectionChildItemBinding) : BaseVH<MCollection>(binding) {
        init {
            binding.root.setOnClickListener {
                getItem {
                    listener?.onCollectionClick(it.key)
                }
            }
        }

        override fun onBind(data: MCollection) {
            binding.ivFavoriteCollectionChildThumbnail.loadImage(data.thumbnail)
        }
    }

    interface IListener {
        fun onCollectionClick(collectionId: String?)
    }
}
