package com.tiger.ar.museum.presentation.explore

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.extension.loadImageBlur
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.ExploreExhibitionItemBinding
import com.tiger.ar.museum.databinding.ExploreItemItemBinding
import com.tiger.ar.museum.domain.model.Exhibition
import com.tiger.ar.museum.domain.model.Item

class ExploreAdapter : MuseumAdapter() {
    companion object {
        const val EXHIBITION_TYPE = 2002
        const val ITEM_TYPE = 2003

        const val LIKE_PAYLOAD = 3001
    }

    var listener: IListener? = null

    override fun getItemViewTypeCustom(position: Int): Int {
        return when (getDataAtPosition(position)) {
            is Exhibition -> EXHIBITION_TYPE
            is Item -> ITEM_TYPE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            EXHIBITION_TYPE -> R.layout.explore_exhibition_item
            ITEM_TYPE -> R.layout.explore_item_item
            else -> INVALID_RESOURCE
        }
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            EXHIBITION_TYPE -> ExhibitionVH(binding as ExploreExhibitionItemBinding)
            ITEM_TYPE -> ItemVH(binding as ExploreItemItemBinding)
            else -> null
        }
    }

    override fun getDiffUtil(oldList: List<Any>, newList: List<Any>): DiffUtil.Callback {
        return ExploreDiffUtil(oldList, newList)
    }

    inner class ExhibitionVH(private val binding: ExploreExhibitionItemBinding) : BaseVH<Exhibition>(binding) {
        init {
            binding.flExploreExhibitionBuyTicket.setOnSafeClick { getItem { listener?.onBuyTicket(it) } }
        }

        override fun onBind(data: Exhibition) {
            binding.ivExploreExhibitionThumb.loadImage(data.thumbnail)
            binding.ivExploreExhibitionMuseumThumb.loadImage(data.museumThumb)
            binding.tvExploreExhibitionName.text = data.name
            binding.tvExploreExhibitionMuseumInfo.text = data.getMuseumInfo()
            binding.tvExploreExhibitionDate.text = data.getExhibitionDate()
            binding.tvExploreExhibitionTitle.text = data.getExhibitionTitle()
            binding.ivExploreExhibitionBackground.loadImageBlur(data.thumbnail)
        }
    }

    inner class ItemVH(private val binding: ExploreItemItemBinding) : BaseVH<Item>(binding) {
        init {
            binding.ivExploreItemLike.setOnSafeClick { getItem { listener?.onLikeItem(it) } }
            binding.ivExploreItemShare.setOnSafeClick { getItem { listener?.onShareItem(it) } }
            binding.ivExploreItemZoom.setOnSafeClick { getItem { listener?.onZoomItem(it) } }
            binding.ivExploreItemThumbnail.setOnSafeClick { getItem { listener?.onZoomItem(it) } }
        }

        override fun onBind(data: Item) {
            binding.ivExploreItemCollectionThumb.loadImage(data.collection?.thumbnail)
            binding.tvExploreItemCollectionName.text = data.collection?.name
            setLikeStatus(data)
            binding.tvExploreItemName.text = data.name
            binding.ivExploreItemBackground.loadImageBlur(data.thumbnail)
            binding.ivExploreItemThumbnail.loadImage(data.thumbnail)
            binding.tvExploreItemCreator.text = data.creator?.name
        }

        override fun onBind(data: Item, payloads: List<Any>) {
            binding.apply {
                (payloads.firstOrNull() as? List<*>)?.forEach {
                    when (it) {
                        LIKE_PAYLOAD -> setLikeStatus(data)
                    }
                }
            }
        }

        private fun setLikeStatus(data: Item) {
            binding.ivExploreItemLike.setImageResource(
                when (data.safeIsLiked()) {
                    true -> R.drawable.ic_like_filled
                    else -> R.drawable.ic_like
                }
            )
        }
    }

    interface IListener {
        fun onLikeItem(item: Item)
        fun onBuyTicket(exhibition: Exhibition)
        fun onZoomItem(item: Item)
        fun onShareItem(item: Item)
    }
}
