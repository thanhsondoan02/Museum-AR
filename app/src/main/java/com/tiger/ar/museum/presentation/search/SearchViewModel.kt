package com.tiger.ar.museum.presentation.search

import androidx.lifecycle.viewModelScope
import com.tiger.ar.museum.common.BaseViewModel
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {
    var suggestTexts: List<SearchKeyAdapter.SearchKeyDisplay> = emptyList()

    fun getSuggestText(inputKey: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            suggestTexts = listOf(
                SearchKeyAdapter.SearchKeyDisplay("Suggest 1", SEARCH_TYPE.HISTORY),
                SearchKeyAdapter.SearchKeyDisplay("Suggest 2", SEARCH_TYPE.HISTORY),
                SearchKeyAdapter.SearchKeyDisplay("Suggest 3", SEARCH_TYPE.HISTORY),
                SearchKeyAdapter.SearchKeyDisplay("Suggest 4"),
                SearchKeyAdapter.SearchKeyDisplay("Suggest 5"),
                SearchKeyAdapter.SearchKeyDisplay("Suggest 6"),
            )
            onSuccess.invoke()
        }
    }

    fun getSearchData(onSuccess: () -> Unit) {
        viewModelScope.launch {
            onSuccess.invoke()
        }
    }
}
