package com.tiger.ar.museum.presentation.collection

import com.tiger.ar.museum.common.recycleview.BaseDiffUtilCallback

class CollectionDiffUtil(oldData: List<Any>, newData: List<Any>) : BaseDiffUtilCallback<Any>(oldData, newData) {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)

       return if (oldItem is CollectionAdapter.HeaderDisplay && newItem is CollectionAdapter.HeaderDisplay) {
           true
       } else {
           oldItem.hashCode() == newItem.hashCode()
       }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)

        return if (oldItem is CollectionAdapter.HeaderDisplay && newItem is CollectionAdapter.HeaderDisplay) {
            oldItem.collection?.safeIsLiked() == newItem.collection?.safeIsLiked()
                    && oldItem.isCollectionTab == newItem.isCollectionTab
        } else {
            true
        }
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)

        val list = mutableListOf<Any>()

        if (oldItem is CollectionAdapter.HeaderDisplay && newItem is CollectionAdapter.HeaderDisplay) {
            if (oldItem.collection?.safeIsLiked() != newItem.collection?.safeIsLiked()) list.add(CollectionAdapter.FOLLOW_PAYLOAD)
            if (oldItem.isCollectionTab != newItem.isCollectionTab) list.add(CollectionAdapter.TAB_PAYLOAD)
        }

        return list.ifEmpty { null }
    }
}

