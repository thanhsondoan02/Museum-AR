package com.tiger.ar.museum.presentation.item

import android.annotation.SuppressLint
import android.util.TypedValue
import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.FontSpan
import com.tiger.ar.museum.common.SpannableBuilder
import com.tiger.ar.museum.common.extension.*
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.*
import com.tiger.ar.museum.domain.model.ItemDetail
import com.tiger.ar.museum.presentation.favorite.item.ItemDisplay
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class ItemAdapter : MuseumAdapter() {
    companion object {
        const val TRANSPARENT_TYPE = 1408
        const val CHOOSE_ACTION_TYPE = 1409
        const val TITLE_TYPE = 1410
        const val DESCRIPTION_TYPE = 1411
        const val DETAIL_TITLE_TYPE = 1414
        const val DETAIL_INFO_TYPE = 1412
        const val RECOMMEND_TYPE = 1413
    }

    var listener: IListener? = null

    override fun getItemViewTypeCustom(position: Int): Int {
        return when (getDataAtPosition(position)) {
            is TransparentDisplay -> TRANSPARENT_TYPE
            is ChooseActionDisplay -> CHOOSE_ACTION_TYPE
            is TitleDisplay -> TITLE_TYPE
            is DescriptionDisplay -> DESCRIPTION_TYPE
            is DetailTitleDisplay -> DETAIL_TITLE_TYPE
            is DetailInfoDisplay -> DETAIL_INFO_TYPE
            is RecommendDisplay -> RECOMMEND_TYPE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            TRANSPARENT_TYPE -> R.layout.item_transparent_item
            CHOOSE_ACTION_TYPE -> R.layout.item_choose_action_item
            TITLE_TYPE -> R.layout.item_title_item
            DESCRIPTION_TYPE -> R.layout.item_description_item
            DETAIL_TITLE_TYPE -> R.layout.item_detail_title_item
            DETAIL_INFO_TYPE -> R.layout.item_detail_info_item
            RECOMMEND_TYPE -> R.layout.item_recommend_item
            else -> throw IllegalArgumentException("getLayoutResource: viewType is invalid")
        }
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            TRANSPARENT_TYPE -> TransparentVH(binding as ItemTransparentItemBinding)
            CHOOSE_ACTION_TYPE -> ChooseActionVH(binding as ItemChooseActionItemBinding)
            TITLE_TYPE -> TitleVH(binding as ItemTitleItemBinding)
            DESCRIPTION_TYPE -> DescriptionVH(binding as ItemDescriptionItemBinding)
            DETAIL_TITLE_TYPE -> DetailTitleVH(binding as ItemDetailTitleItemBinding)
            DETAIL_INFO_TYPE -> DetailInfoVH(binding as ItemDetailInfoItemBinding)
            RECOMMEND_TYPE -> RecommendVH(binding as ItemRecommendItemBinding)
            else -> throw IllegalArgumentException("onCreateViewHolder: viewType is invalid")
        }
    }

    class TransparentDisplay {

    }

    class ChooseActionDisplay {
        var actions: List<ItemActionAdapter.ActionDisplay>? = null
    }

    class TitleDisplay {
        var title: String? = null
        var creator: String? = null
        var time: String? = null
        var collectionName: String? = null
        var collectionThumb: String? = null
        var isLike: Boolean = false
    }

    class DescriptionDisplay {
        var description: String? = null
    }

    class DetailTitleDisplay {
        var isOpen = false
    }

    class DetailInfoDisplay {
        var itemDetail: ItemDetail? = null
    }

    class RecommendDisplay {
        var list: List<ItemDisplay>? = null
    }

    inner class TransparentVH(private val binding: ItemTransparentItemBinding) : BaseVH<TransparentDisplay>(binding) {
        init {
            updateHeight()
        }

        private fun updateHeight() {
            val tv = TypedValue()
            if (getApplication().theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                val actionBarSize = TypedValue.complexToDimensionPixelSize(tv.data, getApplication().resources.displayMetrics)
                val screenHeight = getApplication().resources.displayMetrics.heightPixels - getApplication().getStatusBarHeight()
                binding.vItemTransparent.layoutParams.apply {
                    height = screenHeight - getAppDimensionPixel(R.dimen.dimen_220) - actionBarSize
                }
            }
        }
    }

    inner class ChooseActionVH(private val binding: ItemChooseActionItemBinding) : BaseVH<ChooseActionDisplay>(binding) {
        private val actionAdapter by lazy { ItemActionAdapter() }

        init {
            actionAdapter.listener = object : ItemActionAdapter.IListener {
                override fun onActionClick(actionType: ACTION_TYPE) {
                    listener?.onActionClick(actionType)
                }
            }
            binding.cvItem.apply {
                setAdapter(actionAdapter)
                setLayoutManager(COLLECTION_MODE.HORIZONTAL)
            }
        }

        override fun onBind(data: ChooseActionDisplay) {
            binding.cvItem.submitList(data.actions)
        }
    }

    inner class TitleVH(private val binding: ItemTitleItemBinding) : BaseVH<TitleDisplay>(binding) {
        init {
            binding.ivItemLike.setOnSafeClick {
                getItem {
                    if (it.isLike) {
                        listener?.onDislikeClick()
                    } else {
                        listener?.onLikeClick()
                    }
                }
            }
            binding.ivItemShare.setOnSafeClick {
                listener?.onShareClick()
            }
        }

        @SuppressLint("SetTextI18n")
        override fun onBind(data: TitleDisplay) {
            binding.apply {
                tvItemName.text = data.title
                tvItemCreator.text = data.creator + "  " + data.time
                tvItemCollection.text = data.collectionName
                ivItemCollectionThumb.loadImage(data.collectionThumb)
            }
        }
    }

    inner class DescriptionVH(private val binding: ItemDescriptionItemBinding) : BaseVH<DescriptionDisplay>(binding) {
        init {
            binding.tvItemDescriptionReadMore.setOnSafeClick {
                binding.tvItemDescription.apply {
                    if (maxLines == 3) {
                        maxLines = Integer.MAX_VALUE
                    } else {
                        maxLines = 3
                    }
                }
            }
        }

        override fun onBind(data: DescriptionDisplay) {
            binding.tvItemDescription.text = data.description
        }
    }

    inner class DetailTitleVH(private val binding: ItemDetailTitleItemBinding) : BaseVH<DetailTitleDisplay>(binding) {
        init {
            binding.flItemDetailTitleContainer.setOnSafeClick {
                getItem {
                    if (it.isOpen) {
                        binding.ivItemDetailTitleArrow.animate().rotationBy(-180f).start()
                    } else {
                        binding.ivItemDetailTitleArrow.animate().rotationBy(180f).start()
                    }
                    listener?.onDetailTitleClick(it.isOpen)
                }
            }
        }
    }

    inner class DetailInfoVH(private val binding: ItemDetailInfoItemBinding) : BaseVH<DetailInfoDisplay>(binding) {
        @SuppressLint("SetTextI18n")
        override fun onBind(data: DetailInfoDisplay) {
            binding.tvItemDetailInfo.text = SpannableBuilder().appendText(data.itemDetail?.title)
                .withSpan(FontSpan(getAppFont(R.font.roboto_bold)))
                .appendText(data.itemDetail?.description)
                .withSpan(FontSpan(getAppFont(R.font.roboto_regular)))
                .spannedText
        }
    }

    inner class RecommendVH(private val binding: ItemRecommendItemBinding) : BaseVH<RecommendDisplay>(binding) {
        override fun onBind(data: RecommendDisplay) {
        }
    }

    interface IListener {
        fun onActionClick(actionType: ACTION_TYPE)
        fun onDetailTitleClick(isOpen: Boolean)
        fun onLikeClick()
        fun onDislikeClick()
        fun onShareClick()
    }
}