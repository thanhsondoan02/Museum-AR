package com.tiger.ar.museum.presentation.collection

import androidx.lifecycle.viewModelScope
import com.tiger.ar.museum.common.BaseUseCase
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.common.extension.failure
import com.tiger.ar.museum.common.extension.loading
import com.tiger.ar.museum.common.extension.onException
import com.tiger.ar.museum.common.extension.success
import com.tiger.ar.museum.common.usecase.FlowResult
import com.tiger.ar.museum.domain.model.MCollection
import com.tiger.ar.museum.domain.usecase.GetListCollectionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CollectionViewModel : BaseViewModel() {
    private var _get3dModelState = MutableStateFlow(FlowResult.newInstance<List<MCollection>>())
    val get3dModelState = _get3dModelState.asStateFlow()

    fun getListCollection() {
        viewModelScope.launch(Dispatchers.IO) {
            GetListCollectionUseCase().invoke(BaseUseCase.VoidRequest())
                .onStart { _get3dModelState.loading() }
                .onException {
                    _get3dModelState.failure(it)
                }
                .collect {
                    _get3dModelState.success(it)
                }
        }
    }
}
