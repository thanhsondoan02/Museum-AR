package com.tiger.ar.museum.presentation.download2

import com.tiger.ar.museum.common.recycleview.BaseDiffUtilCallback

class Download2DiffUtil(oldData: List<Any>, newData: List<Any>) : BaseDiffUtilCallback<Any>(oldData, newData) {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition) as DownloadFile
        val newItem = getNewItem(newItemPosition) as DownloadFile

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition) as DownloadFile
        val newItem = getNewItem(newItemPosition) as DownloadFile
        return  oldItem.name == newItem.name &&
                oldItem.percent == newItem.percent &&
                oldItem.status == newItem.status
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = getOldItem(oldItemPosition) as DownloadFile
        val newItem = getNewItem(newItemPosition) as DownloadFile

        val list = mutableListOf<Any>()

        if (oldItem.name != newItem.name) list.add(Download2Adapter.NAME_PAYLOAD)
        if (oldItem.percent != newItem.percent) list.add(Download2Adapter.PERCENT_PAYLOAD)
        if (oldItem.status != newItem.status) list.add(Download2Adapter.STATUS_PAYLOAD)

        return list.ifEmpty { null }
    }
}
