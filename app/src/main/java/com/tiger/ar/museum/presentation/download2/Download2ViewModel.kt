package com.tiger.ar.museum.presentation.download2

import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.common.usecase.FlowResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class Download2ViewModel : BaseViewModel() {
    private var _updateListState = MutableStateFlow(FlowResult.newInstance<List<DownloadFile>>())
    val updateListState = _updateListState.asStateFlow()

    private var _startDownloadState = MutableStateFlow(FlowResult.newInstance<DownloadFile>())
    val startDownloadState = _startDownloadState.asStateFlow()

}
