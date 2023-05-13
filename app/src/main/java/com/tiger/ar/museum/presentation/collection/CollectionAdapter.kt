package com.tiger.ar.museum.presentation.collection

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.getAppColor
import com.tiger.ar.museum.common.extension.gone
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.extension.show
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.CollectionHeaderItemBinding
import com.tiger.ar.museum.domain.model.MCollection
import com.tiger.ar.museum.presentation.favorite.FavoriteAdapter

class CollectionAdapter : MuseumAdapter() {
    companion object {
        const val HEADER_VIEW_TYPE = 1409

        const val FOLLOW_PAYLOAD = "FOLLOW_PAYLOAD"
        const val TAB_PAYLOAD = "TAB_PAYLOAD"
    }

    var listener: IListener? = null

    override fun getDiffUtil(oldList: List<Any>, newList: List<Any>): DiffUtil.Callback {
        return CollectionDiffUtil(oldList, newList)
    }

    override fun getItemViewTypeCustom(position: Int): Int {
        return when (getDataAtPosition(position)) {
            is HeaderDisplay -> FavoriteAdapter.HEADER_VIEW_TYPE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            FavoriteAdapter.HEADER_VIEW_TYPE -> R.layout.collection_header_item
            else -> throw IllegalArgumentException("getLayoutResource: viewType is invalid")
        }
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            HEADER_VIEW_TYPE -> HeaderVH(binding as CollectionHeaderItemBinding)
            else -> throw IllegalArgumentException("onCreateViewHolder: viewType is invalid")
        }
    }

    inner class HeaderVH(private val binding: CollectionHeaderItemBinding) : BaseVH<HeaderDisplay>(binding) {

        init {
            binding.mcvCollectionHeaderFollow.setOnSafeClick {
                getItem {
                    val isFollow = it.collection?.safeIsLiked() ?: false
                    if (isFollow) {
                        listener?.onUnFollowClick()
                    } else {
                        listener?.onFollowClick()
                    }
                }
            }

            binding.flFavoriteHeaderTabFavorite.setOnSafeClick {
                listener?.onCollectionTabClick()
            }

            binding.flFavoriteHeaderTabGalleries.setOnSafeClick {
                listener?.onVisitTabClick()
            }

            binding.ivCollectionHeaderShare.setOnSafeClick {
                listener?.onShareClick()
            }
        }

        override fun onBind(data: HeaderDisplay) {
            binding.apply {
                ivCollectionHeaderThumbnail.loadImage(data.collection?.thumbnail)
                ivCollectionHeaderIcon.loadImage(data.collection?.icon)
                tvCollectionHeaderName.text = data.collection?.name
                tvCollectionHeaderPlace.text = data.collection?.place
            }
            setFollowStatus(data)
            setTab(data)
        }

        override fun onBind(data: HeaderDisplay, payloads: List<Any>) {
            binding.apply {
                (payloads.firstOrNull() as? List<*>)?.forEach {
                    when (it) {
                        FOLLOW_PAYLOAD -> setFollowStatus(data)
                        TAB_PAYLOAD -> setTab(data)
                    }
                }
            }
        }

        private fun setTab(data: HeaderDisplay) {
            if (data.isCollectionTab) {
                selectTabFavorite()
            } else {
                selectTabGalleries()
            }
        }

        private fun selectTabFavorite() {
            binding.vFavoriteHeaderTabFavoriteEnable.show()
            binding.vFavoriteHeaderTabFavoriteDisable.gone()
            binding.vFavoriteHeaderTabGalleriesEnable.gone()
            binding.vFavoriteHeaderTabGalleriesDisable.show()
            binding.tvFavoriteTabFavorite.setTextColor(getAppColor(R.color.main_black))
            binding.tvFavoriteTabGalleries.setTextColor(getAppColor(R.color.gray))
        }

        private fun selectTabGalleries() {
            binding.vFavoriteHeaderTabFavoriteEnable.gone()
            binding.vFavoriteHeaderTabFavoriteDisable.show()
            binding.vFavoriteHeaderTabGalleriesEnable.show()
            binding.vFavoriteHeaderTabGalleriesDisable.gone()
            binding.tvFavoriteTabFavorite.setTextColor(getAppColor(R.color.gray))
            binding.tvFavoriteTabGalleries.setTextColor(getAppColor(R.color.main_black))
        }

        private fun setFollowStatus(data: HeaderDisplay) {
            val isFollow = data.collection?.safeIsLiked() ?: false
            if (isFollow) {
                binding.llCollectionHeaderFollowing.show()
                binding.llCollectionHeaderFollow.gone()
            } else {
                binding.llCollectionHeaderFollowing.gone()
                binding.llCollectionHeaderFollow.show()
            }
        }
    }

    class HeaderDisplay {
        var collection: MCollection? = null
        var isCollectionTab: Boolean = true

        fun copy(): HeaderDisplay {
            val newItem = HeaderDisplay()
            newItem.collection = collection?.copy()?.apply {
                key = collection?.key
                mapIsLiked()
            }
            newItem.isCollectionTab = isCollectionTab
            return newItem
        }
    }

    interface IListener {
        fun onItemClick(itemId: String)
        fun onStoryClick(storyId: String)
        fun onFollowClick()
        fun onUnFollowClick()
        fun onCollectionTabClick()
        fun onVisitTabClick()
        fun onShareClick()
    }
}
