package com.tiger.ar.museum.presentation.collection

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.*
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.CollectionHeaderItemBinding
import com.tiger.ar.museum.domain.model.MCollection
import com.tiger.ar.museum.presentation.favorite.FavoriteAdapter

class CollectionAdapter : MuseumAdapter() {
    companion object {
        const val HEADER_VIEW_TYPE = 1409

        const val HEADER_TAB_PAYLOAD = "HEADER_TAB_PAYLOAD"
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
                        listener?.onUnFollowClick()
                    }
                    setFollowStatus(!isFollow)
                }
            }

            binding.flFavoriteHeaderTabFavorite.setOnSafeClick {
                listener?.onCollectionTab()
            }

            binding.flFavoriteHeaderTabGalleries.setOnSafeClick {
                listener?.onVisitTab()
            }
        }

        override fun onBind(data: HeaderDisplay) {
            binding.apply {
                ivCollectionHeaderThumbnail.loadImage(data.collection?.thumbnail)
                ivCollectionHeaderIcon.loadImage(data.collection?.icon)
                tvCollectionHeaderName.text = data.collection?.name
                tvCollectionHeaderPlace.text = data.collection?.place
            }
            setFollowStatus(data.collection?.safeIsLiked() ?: false)
            setTab(data)
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

        private fun setFollowStatus(isFollow: Boolean) {
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
    }

    interface IListener {
        fun onItemClick(itemId: String)
        fun onStoryClick(storyId: String)
        fun onFollowClick()
        fun onUnFollowClick()
        fun onCollectionTab()
        fun onVisitTab()
    }
}
