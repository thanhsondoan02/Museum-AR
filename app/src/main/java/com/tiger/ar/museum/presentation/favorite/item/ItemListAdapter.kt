package com.tiger.ar.museum.presentation.favorite.item

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.getAppDimensionPixel
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.databinding.ItemListItemBinding
import com.tiger.ar.museum.databinding.ItemListTitleItemBinding
import com.tiger.ar.museum.presentation.widget.PeswocAdapter

class ItemListAdapter : PeswocAdapter() {
    companion object {
        const val TITLE_TYPE = 1410
        const val ITEM_TYPE = 1411
    }

    override fun getItemViewTypeCustom(position: Int): Int {
        return if (position == 0) {
            TITLE_TYPE
        } else {
            ITEM_TYPE
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            TITLE_TYPE -> R.layout.item_list_title_item
            ITEM_TYPE -> R.layout.item_list_item
            else -> throw IllegalArgumentException("getLayoutResource: viewType is invalid")
        }
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            TITLE_TYPE -> TitleVH(binding as ItemListTitleItemBinding)
            ITEM_TYPE -> ItemVH(binding as ItemListItemBinding)
            else -> throw IllegalArgumentException("onCreateViewHolder: viewType is invalid")
        }
    }

    inner class TitleVH(private val binding: ItemListTitleItemBinding) : BaseVH<Int>(binding) {
        override fun onBind(data: Int) {
            binding.tvItemListTitle.text = getTitleText(data)
        }

        private fun getTitleText(data: Int): String {
            return getAppString(R.string.items) + " - " + data
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
