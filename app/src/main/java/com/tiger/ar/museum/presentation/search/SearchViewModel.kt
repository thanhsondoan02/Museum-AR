package com.tiger.ar.museum.presentation.search

import androidx.lifecycle.viewModelScope
import com.tiger.ar.museum.common.BaseViewModel
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {
    var suggestTexts: List<String> = emptyList()

    fun getSuggestText(inputKey: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            suggestTexts = listOf(inputKey)
            onSuccess.invoke()
        }
    }

}
