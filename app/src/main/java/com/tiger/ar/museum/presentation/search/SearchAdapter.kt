package com.tiger.ar.museum.presentation.search

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter

class SearchAdapter: MuseumAdapter() {
    companion object {
        const val ITEM_VIEW_TYPE = 1410
        const val STORY_VIEW_TYPE = 1411
        const val EXHIBIT_VIEW_TYPE = 1412
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            ITEM_VIEW_TYPE -> R.layout.search_key_item
            STORY_VIEW_TYPE -> R.layout.search_story_item
            EXHIBIT_VIEW_TYPE -> R.layout.search_exhibit_item
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        TODO("Not yet implemented")
    }
}
