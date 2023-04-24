package com.tiger.ar.museum.presentation.camera.view3d

import androidx.lifecycle.viewModelScope
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.firebase.FirebaseApp
import com.tiger.ar.museum.common.BaseUseCase
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.common.extension.*
import com.tiger.ar.museum.common.usecase.FlowResult
import com.tiger.ar.museum.domain.model.Model3d
import com.tiger.ar.museum.domain.usecase.GetAllModelUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.File

class View3dViewModel : BaseViewModel() {
    var model3d: ModelRenderable? = null
    var tempFile: File? = null

    private var _get3dModelListState = MutableStateFlow(FlowResult.newInstance<List<Model3d>>())
    val get3dModelListState = _get3dModelListState.asStateFlow()

    init {
        FirebaseApp.initializeApp(getApplication())
    }

    fun get3dModelList() {
        viewModelScope.launch(Dispatchers.IO) {
            GetAllModelUseCase().invoke(BaseUseCase.VoidRequest())
                .onStart { _get3dModelListState.loading() }
                .onException { _get3dModelListState.failure(it) }
                .collect { _get3dModelListState.success(it) }
        }
    }
}
