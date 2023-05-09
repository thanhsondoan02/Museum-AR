package com.tiger.ar.museum.presentation.widget

import com.tiger.ar.museum.common.recycleview.BaseAdapter
import com.tiger.ar.museum.presentation.favorite.item.ItemDisplay

abstract class PeswocAdapter : BaseAdapter() {

    fun getItemSpanSize(position: Int, spanCount: Int): Int {
        val item = getDataAtPosition(position) as ItemDisplay
        when (item.countInRow) {
            1 -> {
                return spanCount
            }
            2 -> {
                return spanCount / 2
            }
            else -> {
                throw IllegalArgumentException("getItemSpanSize: maxItemHorizontal must be 1 or 2")
            }
        }
    }
}

