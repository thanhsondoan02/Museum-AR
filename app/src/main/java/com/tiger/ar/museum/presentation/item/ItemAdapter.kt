package com.tiger.ar.museum.presentation.item

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.getAppDimensionPixel
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.ItemListItemBinding
import com.tiger.ar.museum.databinding.ItemListTitleItemBinding
import com.tiger.ar.museum.presentation.favorite.item.ItemDisplay

class ItemAdapter : MuseumAdapter() {
    companion object {
        const val IMAGE_TYPE = 1410
        const val DESCRIPTION_TYPE = 1411
        const val DETAIL_TYPE = 1412
        const val RECOMMEND_TYPE = 1413
    }

    override fun getItemViewTypeCustom(position: Int): Int {
        return when (getDataAtPosition(position)) {
            is ImageDisplay -> IMAGE_TYPE
            is DescriptionDisplay -> DESCRIPTION_TYPE
            is DetailDisplay -> DETAIL_TYPE
            is RecommendDisplay -> RECOMMEND_TYPE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
//            IMAGE_TYPE -> R.layout.item_image_item
//            DESCRIPTION_TYPE -> R.layout.item_description_item
//            DETAIL_TYPE -> R.layout.item_detail_item
//            RECOMMEND_TYPE -> R.layout.item_recommend_item
            else -> throw IllegalArgumentException("getLayoutResource: viewType is invalid")
        }
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
//            TITLE_TYPE -> TitleVH(binding as ItemListTitleItemBinding)
//            ITEM_TYPE -> ItemVH(binding as ItemListItemBinding)
            else -> throw IllegalArgumentException("onCreateViewHolder: viewType is invalid")
        }
    }

    class ImageDisplay {
        var image: String? = null
    }

    class DescriptionDisplay {
        var description: String? = null
    }

    class DetailDisplay {
        var detail: String? = null
    }

    class RecommendDisplay {
        var recommend: String? = null
    }

    inner class TitleVH(private val binding: ItemListTitleItemBinding) : BaseVH<Int>(binding) {
        override fun onBind(data: Int) {
        }
    }

    inner class ItemVH(private val binding: ItemListItemBinding) : BaseVH<ItemDisplay>(binding) {
        override fun onBind(data: ItemDisplay) {
            binding.ivItemList.loadImage(data.item?.thumbnail)

            if (data.isLeft == true) {
                binding.ivItemList.layoutParams = (binding.ivItemList.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    rightMargin = getAppDimensionPixel(R.dimen.dimen_2)
                }
            } else if (data.isLeft == false) {
                binding.ivItemList.layoutParams = (binding.ivItemList.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    leftMargin = getAppDimensionPixel(R.dimen.dimen_2)
                }
            }
        }
    }
}
