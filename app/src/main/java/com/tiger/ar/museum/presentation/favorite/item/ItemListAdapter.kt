package com.tiger.ar.museum.presentation.favorite.item

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.getAppDimensionPixel
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.databinding.ItemListItemBinding
import com.tiger.ar.museum.presentation.widget.PeswocAdapter

class ItemListAdapter : PeswocAdapter() {

    override fun getLayoutResource(viewType: Int) = R.layout.item_list_item

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return ItemVH(binding as ItemListItemBinding)
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
