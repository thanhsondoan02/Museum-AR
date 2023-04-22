package com.tiger.ar.museum.common.recycleview

abstract class MuseumAdapter : BaseAdapter() {
    fun <DATA> BaseVH<DATA>.getItem(onAction: (DATA) -> Unit) {
        val item = getDataAtPosition(absoluteAdapterPosition) as? DATA
        item?.let {
            onAction.invoke(it)
        }
    }
}

