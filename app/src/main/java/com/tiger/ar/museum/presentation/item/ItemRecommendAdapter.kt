package com.tiger.ar.museum.presentation.item

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.ItemRecommendSubItemBinding
import com.tiger.ar.museum.domain.model.Item

class ItemRecommendAdapter: MuseumAdapter() {
    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.item_recommend_sub_item
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return ItemVH(binding as ItemRecommendSubItemBinding)
    }

    inner class ItemVH(private val binding: ItemRecommendSubItemBinding) : BaseVH<Item>(binding) {
        init {
            binding.root.setOnSafeClick { getItem { listener?.onClickItem(it.key) } }
        }

        override fun onBind(data: Item) {
            binding.tvItemRecommendSubName.text = data.name
            binding.ivItemRecommendSub.loadImage(data.thumbnail)
        }
    }

    interface IListener {
        fun onClickItem(itemId: String?)
    }
}
