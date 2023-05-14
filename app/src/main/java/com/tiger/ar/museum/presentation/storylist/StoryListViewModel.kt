package com.tiger.ar.museum.presentation.storylist

import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.domain.model.Story

class StoryListViewModel : BaseViewModel() {
    var stories: List<Story> = emptyList()
}
