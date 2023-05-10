package com.tiger.ar.museum.presentation.item

import androidx.lifecycle.viewModelScope
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.domain.model.Item
import kotlinx.coroutines.launch

class ItemViewModel : BaseViewModel() {
    var item: Item? = null
    var itemData: MutableList<Any> = mutableListOf()

    fun mapDataForAdapter(onSuccess: () -> Unit) {
        viewModelScope.launch {

        }
    }
}
