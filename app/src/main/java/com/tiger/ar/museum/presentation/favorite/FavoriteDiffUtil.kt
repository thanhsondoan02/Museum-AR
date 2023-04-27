package com.tiger.ar.museum.presentation.favorite

import com.tiger.ar.museum.common.recycleview.BaseDiffUtilCallback
import com.tiger.ar.museum.domain.model.Gallery

class FavoriteDiffUtil(oldData: List<Any>, newData: List<Any>) : BaseDiffUtilCallback<Any>(oldData, newData) {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(0)
        val newItem = getNewItem(0)

        return !(oldItem is Gallery && newItem is Gallery)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)

        return oldItem is FavoriteAdapter.HeaderDisplay && newItem is FavoriteAdapter.HeaderDisplay
    }

//    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
//        val oldItem = (getOldItem(0) as? GiftStoreInfo)
//        val newItem = (getNewItem(0) as? GiftStoreInfo)
//
//        val list = mutableListOf<Any>()
//
//        if (oldItem?.imageThumbs?.firstOrNull() != newItem?.imageThumbs?.firstOrNull()) list.add(GiftStoreAdapter.GIFT_THUMBNAIL_PAYLOAD)
//        if (oldItem?.pointRequest != newItem?.pointRequest) list.add(GiftStoreAdapter.GIFT_POINT_PAYLOAD)
//        if (oldItem?.prizeName != newItem?.prizeName) list.add(GiftStoreAdapter.GIFT_NAME_PAYLOAD)
//
//        return list.ifEmpty { null }
//    }
}

