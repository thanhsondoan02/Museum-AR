package com.tiger.ar.museum.presentation.widget

import com.tiger.ar.museum.common.recycleview.BaseAdapter
import com.tiger.ar.museum.presentation.favorite.item.ItemDisplay

abstract class PeswocAdapter : BaseAdapter() {
    companion object {
        const val SPAN_COUNT = 10E4.toInt()
    }

    fun getItemSpanSize(position: Int): Int {
        val item = getDataAtPosition(position) as? ItemDisplay
        return if (item?.countInRow == 2) {
            item.spanSize
        } else {
            SPAN_COUNT
        }
    }
}

