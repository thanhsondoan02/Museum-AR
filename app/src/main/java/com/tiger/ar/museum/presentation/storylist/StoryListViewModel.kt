package com.tiger.ar.museum.presentation.storylist

import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.domain.model.StreetView

class StoryListViewModel : BaseViewModel() {
    var streetViews: List<StreetView> = emptyList()
}
