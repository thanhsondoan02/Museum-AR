package com.tiger.ar.museum.presentation.favorite.item

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.databinding.ItemListItemBinding
import com.tiger.ar.museum.presentation.widget.PeswocAdapter

class ItemListAdapter: PeswocAdapter() {

    override fun getItemCountInRow(viewType: Int): Int {
        return 2
    }

    override fun getLayoutResource(viewType: Int) = R.layout.item_list_item

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return ItemVH(binding as ItemListItemBinding)
    }

    inner class ItemVH(private val binding: ItemListItemBinding) : BaseVH<ItemDisplay>(binding) {
        override fun onBind(data: ItemDisplay) {
            binding.ivItemList.loadImage(data.item?.thumbnail)
        }
    }
}
