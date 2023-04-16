package com.tiger.ar.museum.presentation.ar

import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.common.extension.failure
import com.tiger.ar.museum.common.extension.getApplication
import com.tiger.ar.museum.common.usecase.FlowResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class ArViewModel : BaseViewModel() {
    var modelName: String? = null
    var file: File? = null
    var renderable: ModelRenderable? = null
    var onGet3DModelLoading: (() -> Unit)? = null
    var onGet3DModelSuccess: (() -> Unit)? = null
    var onGet3DModelFailure: (() -> Unit)? = null

    private var _get3dModelState = MutableStateFlow(FlowResult.newInstance<Boolean>())
    val get3dModelState = _get3dModelState.asStateFlow()

    fun get3dModel() {
//        val rv = Get3dModelUseCase.Get3dModelUseCaseRV(modelName)
//        viewModelScope.launch(Dispatchers.IO) {
//            Get3dModelUseCase().invoke(rv)
//                .onStart { _get3dModelState.loading() }
//                .onException { _get3dModelState.failure(it) }
//                .collect {
//                    file = it
//                    _get3dModelState.success(true)
//                }
//        }

        // start
        onGet3DModelLoading?.invoke()
        FirebaseApp.initializeApp(getApplication())
        val storage = FirebaseStorage.getInstance()

        // check model name
        if (modelName == null) {
            _get3dModelState.failure(Exception("Model name is null"))
            return
        }
        val modelRef = storage.reference.child(modelName!!)

        // check file
        file = File.createTempFile("table", "glb")
        if (file == null) {
            _get3dModelState.failure(Exception("File is null"))
            return
        }

        // download file
        val task = modelRef.getFile(file!!)
        task.addOnFailureListener {
            onGet3DModelFailure?.invoke()
        }
        task.addOnSuccessListener {
            onGet3DModelSuccess?.invoke()
        }
    }
}
