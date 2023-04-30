package com.tiger.ar.museum.presentation.favorite

import com.tiger.ar.museum.common.recycleview.BaseDiffUtilCallback
import com.tiger.ar.museum.domain.model.Gallery

class FavoriteDiffUtil(oldData: List<Any>, newData: List<Any>) : BaseDiffUtilCallback<Any>(oldData, newData) {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)

        return (oldItem is FavoriteAdapter.HeaderDisplay && newItem is FavoriteAdapter.HeaderDisplay)
                || (oldItem is FavoriteAdapter.ItemDisplay && newItem is FavoriteAdapter.ItemDisplay)
                || (oldItem is FavoriteAdapter.StoryDisplay && newItem is FavoriteAdapter.StoryDisplay)
                || (oldItem is FavoriteAdapter.CollectionDisplay && newItem is FavoriteAdapter.CollectionDisplay)
                || (oldItem is Gallery && newItem is Gallery && oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)

        return if (oldItem is FavoriteAdapter.HeaderDisplay && newItem is FavoriteAdapter.HeaderDisplay) {
            oldItem.isFavoriteTab == newItem.isFavoriteTab
                    && oldItem.avatarUrl == newItem.avatarUrl
        } else {
            false
        }
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)

        val list = mutableListOf<Any>()

        if (oldItem is FavoriteAdapter.HeaderDisplay && newItem is FavoriteAdapter.HeaderDisplay) {
            if (oldItem.isFavoriteTab != newItem.isFavoriteTab) list.add(FavoriteAdapter.HEADER_TAB_PAYLOAD)
            if (oldItem.avatarUrl != newItem.avatarUrl) list.add(FavoriteAdapter.AVATAR_PAYLOAD)
        }

        return list.ifEmpty { null }
    }
}

