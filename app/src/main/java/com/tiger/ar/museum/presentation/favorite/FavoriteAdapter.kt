package com.tiger.ar.museum.presentation.favorite

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.*
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.*
import com.tiger.ar.museum.domain.model.Gallery
import com.tiger.ar.museum.domain.model.Item
import com.tiger.ar.museum.domain.model.MCollection
import com.tiger.ar.museum.domain.model.Story
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class FavoriteAdapter : MuseumAdapter() {
    companion object {
        const val HEADER_VIEW_TYPE = 1409
        const val ITEM_VIEW_TYPE = 1410
        const val STORY_VIEW_TYPE = 1411
        const val COLLECTION_VIEW_TYPE = 1412
        const val GALLERY_VIEW_TYPE = 1413
    }

    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            HEADER_VIEW_TYPE -> R.layout.favorite_header_item
            ITEM_VIEW_TYPE -> R.layout.favorite_item_item
            STORY_VIEW_TYPE -> R.layout.favorite_story_item
            COLLECTION_VIEW_TYPE -> R.layout.favorite_collection_item
            GALLERY_VIEW_TYPE -> R.layout.favorite_gallery_item
            else -> INVALID_RESOURCE
        }
    }

    override fun getItemViewTypeCustom(position: Int): Int {
        return when (getDataAtPosition(position)) {
            is HeaderDisplay -> HEADER_VIEW_TYPE
            is ItemDisplay -> ITEM_VIEW_TYPE
            is StoryDisplay -> STORY_VIEW_TYPE
            is CollectionDisplay -> COLLECTION_VIEW_TYPE
            is Gallery -> GALLERY_VIEW_TYPE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getDiffUtil(oldList: List<Any>, newList: List<Any>): DiffUtil.Callback {
        return FavoriteDiffUtil(oldList, newList)
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            HEADER_VIEW_TYPE -> HeaderVH(binding as FavoriteHeaderItemBinding)
            ITEM_VIEW_TYPE -> ItemVH(binding as FavoriteItemItemBinding)
            STORY_VIEW_TYPE -> StoryVH(binding as FavoriteStoryItemBinding)
            COLLECTION_VIEW_TYPE -> CollectionVH(binding as FavoriteCollectionItemBinding)
            GALLERY_VIEW_TYPE -> GalleryVH(binding as FavoriteGalleryItemBinding)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    class HeaderDisplay {
        var avatarUrl: String? = null
    }

    class ItemDisplay {
        var count: Int? = null
        var itemList: List<Item>? = null
    }

    class StoryDisplay {
        var count: Int? = null
        var storyList: List<Story>? = null
    }

    class CollectionDisplay {
        var count: Int? = null
        var collectionList: List<MCollection>? = null
    }

    inner class HeaderVH(private val binding: FavoriteHeaderItemBinding) : BaseVH<HeaderDisplay>(binding) {
        init {
            binding.flFavoriteHeaderTabFavorite.setOnSafeClick {
                selectTabFavorite()
            }

            binding.flFavoriteHeaderTabGalleries.setOnSafeClick {
                selectTabGalleries()
            }
        }

        override fun onBind(data: HeaderDisplay) {
            binding.ivProfileAvatar.loadImage(data.avatarUrl, placeHolder = getAppDrawable(R.drawable.ic_no_picture))
        }

        private fun selectTabFavorite() {
            binding.vFavoriteHeaderTabFavoriteEnable.show()
            binding.vFavoriteHeaderTabFavoriteDisable.gone()
            binding.vFavoriteHeaderTabGalleriesEnable.gone()
            binding.vFavoriteHeaderTabGalleriesDisable.show()
            binding.tvFavoriteTabFavorite.setTextColor(getAppColor(R.color.main_black))
            binding.tvFavoriteTabGalleries.setTextColor(getAppColor(R.color.gray))
            listener?.onFavoriteTab()
        }

        private fun selectTabGalleries() {
            binding.vFavoriteHeaderTabFavoriteEnable.gone()
            binding.vFavoriteHeaderTabFavoriteDisable.show()
            binding.vFavoriteHeaderTabGalleriesEnable.show()
            binding.vFavoriteHeaderTabGalleriesDisable.gone()
            binding.tvFavoriteTabFavorite.setTextColor(getAppColor(R.color.gray))
            binding.tvFavoriteTabGalleries.setTextColor(getAppColor(R.color.main_black))
            listener?.onGalleriesTab()
        }
    }

    inner class ItemVH(private val binding: FavoriteItemItemBinding) : BaseVH<ItemDisplay>(binding) {
        init {
            binding.tvFavoriteItemViewAll.setOnSafeClick {
                listener?.onViewAllItem()
            }
        }

        override fun onBind(data: ItemDisplay) {
            binding.ivFavoriteItemItem1.loadImage(data.itemList?.getOrNull(0)?.thumbnail)
            binding.ivFavoriteItemItem2.loadImage(data.itemList?.getOrNull(1)?.thumbnail)
            binding.ivFavoriteItemItem3.loadImage(data.itemList?.getOrNull(2)?.thumbnail)
            binding.ivFavoriteItemItem4.loadImage(data.itemList?.getOrNull(3)?.thumbnail)
            binding.tvFavoriteItemCount.text = data.count.toString()
        }
    }

    inner class StoryVH(private val binding: FavoriteStoryItemBinding) : BaseVH<StoryDisplay>(binding) {
        private val adapter: FavoriteStoryAdapter by lazy { FavoriteStoryAdapter() }

        init {
            binding.cvFavoriteStory.apply {
                setAdapter(this@StoryVH.adapter)
                setLayoutManager(COLLECTION_MODE.HORIZONTAL)
            }
            binding.tvFavoriteStoryViewAll.setOnSafeClick {
                listener?.onViewAllStory()
            }
        }

        override fun onBind(data: StoryDisplay) {
            binding.cvFavoriteStory.submitList(data.storyList)
            binding.tvFavoriteStoryCount.text = data.count.toString()
        }
    }

    inner class CollectionVH(private val binding: FavoriteCollectionItemBinding) : BaseVH<CollectionDisplay>(binding) {
        private val adapter: FavoriteCollectionAdapter by lazy { FavoriteCollectionAdapter() }

        init {
            binding.cvFavoriteCollection.apply {
                setAdapter(this@CollectionVH.adapter)
                setLayoutManager(COLLECTION_MODE.HORIZONTAL)
            }
            binding.tvFavoriteCollectionViewAll.setOnSafeClick {
                listener?.onViewAllStory()
            }
        }

        override fun onBind(data: CollectionDisplay) {
            binding.cvFavoriteCollection.submitList(data.collectionList)
            binding.tvFavoriteCollectionCount.text = data.count.toString()
        }
    }

    inner class GalleryVH(private val binding: FavoriteGalleryItemBinding) : BaseVH<Gallery>(binding) {
        init {
            binding.tvFavoriteGalleryMore.setOnSafeClick {
                getItem {
                    listener?.onMoreGallery(it)
                }
            }
        }

        override fun onBind(data: Gallery) {
            binding.tvFavoriteGalleryTitle.text = data.title
            if (data.description.isNullOrEmpty()) {
                binding.tvFavoriteGalleryDescription.gone()
            } else {
                binding.tvFavoriteGalleryDescription.show()
                binding.tvFavoriteGalleryDescription.text = data.description
            }
            when (data.items?.size) {
                1 -> {
                    binding.mcvFavoriteGalleryCountOne.show()
                    binding.llFavoriteGalleryCountTwo.gone()
                    binding.llFavoriteGalleryCountThree.gone()

                    binding.ivFavoriteGalleryCountOne.loadImage(data.items?.getOrNull(0)?.thumbnail)
                }
                2 -> {
                    binding.mcvFavoriteGalleryCountOne.gone()
                    binding.llFavoriteGalleryCountTwo.show()
                    binding.llFavoriteGalleryCountThree.gone()

                    binding.ivFavoriteGalleryCountTwo1.loadImage(data.items?.getOrNull(0)?.thumbnail)
                    binding.ivFavoriteGalleryCountTwo2.loadImage(data.items?.getOrNull(1)?.thumbnail)
                }
                else -> {
                    binding.mcvFavoriteGalleryCountOne.gone()
                    binding.llFavoriteGalleryCountTwo.gone()
                    binding.llFavoriteGalleryCountThree.show()

                    binding.ivFavoriteGalleryCountThree1.loadImage(data.items?.getOrNull(0)?.thumbnail)
                    binding.ivFavoriteGalleryCountThree2.loadImage(data.items?.getOrNull(1)?.thumbnail)
                    binding.ivFavoriteGalleryCountThree3.loadImage(data.items?.getOrNull(2)?.thumbnail)
                }
            }
        }
    }

    interface IListener {
        fun onFavoriteTab()
        fun onGalleriesTab()
        fun onViewAllItem()
        fun onViewAllStory()
        fun onViewAllCollection()
        fun onMoreGallery(gallery: Gallery)
    }
}
