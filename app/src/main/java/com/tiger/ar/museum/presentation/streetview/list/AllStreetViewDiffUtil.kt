package com.tiger.ar.museum.presentation.streetview.list

import com.tiger.ar.museum.common.recycleview.BaseDiffUtilCallback

class AllStreetViewDiffUtil(oldData: List<Any>, newData: List<Any>) : BaseDiffUtilCallback<Any>(oldData, newData) {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)

        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)

        return oldItem.hashCode() == newItem.hashCode()
    }
}

