package com.tiger.ar.museum.presentation.camera.view3d

import android.net.Uri
import android.view.MotionEvent
import androidx.activity.viewModels
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.firebase.storage.FirebaseStorage
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.IViewListener
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.extension.coroutinesLaunch
import com.tiger.ar.museum.common.extension.handleUiState
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.View3dActivityBinding
import com.tiger.ar.museum.domain.model.Model3d
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE
import java.io.File
import java.io.IOException

class View3dActivity: MuseumActivity<View3dActivityBinding>(R.layout.view_3d_activity) {
    private val viewModel by viewModels<View3dViewModel>()
    private val adapter by lazy { View3dAdapter() }
    private var arFragment: ArFragment? = null

    override fun onInitView() {
        super.onInitView()
        initRecyclerView()
        initArFragment()
        viewModel.get3dModelList()
    }

    override fun onObserverViewModel() {
        super.onObserverViewModel()

        coroutinesLaunch(viewModel.get3dModelListState) {
            handleUiState(it, object : IViewListener {
                override fun onLoading() {
                }

                override fun onFailure() {
                    toast("Lấy danh sách mô hình thất bại: ${it.throwable?.message}")
                }

                override fun onSuccess() {
                    binding.cvView3d.submitList(it.data)
                }
            })
        }
    }

    private fun initRecyclerView() {
        adapter.listener = object : View3dAdapter.IListener {
            override fun onDownloadClick(model3d: Model3d) {
                downloadModel3d(model3d)
            }
        }
        binding.cvView3d.apply {
            setAdapter(this@View3dActivity.adapter)
            setLayoutManager(COLLECTION_MODE.HORIZONTAL)
        }
    }

    private fun initArFragment() {
        arFragment = supportFragmentManager.findFragmentById(R.id.fView3dArFragment) as ArFragment
        arFragment?.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane?, motionEvent: MotionEvent? ->
            val anchorNode = AnchorNode(hitResult.createAnchor())
            anchorNode.renderable = viewModel.model3d
            arFragment!!.arSceneView.scene.addChild(anchorNode)
        }
    }

    private fun downloadModel3d(model3d: Model3d) {
        if (model3d.path == null) {
            toast("Tên mô hình null")
        }
        val modelRef = FirebaseStorage.getInstance().reference.child(model3d.path!!)

        try {
            viewModel.tempFile = File.createTempFile("table", "glb")
            modelRef.getFile(viewModel.tempFile!!).apply {
                toast("Bắt đầu tải mô hình ${model3d.path}")
                addOnSuccessListener { buildModel() }
                addOnFailureListener { toast("Lỗi tải mô hình: ${it.message}") }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            toast("Lỗi tải mô hình: ${e.message}")
        }
    }

    private fun buildModel() {
        val renderableSource = RenderableSource
            .builder()
            .setSource(this, Uri.parse(viewModel.tempFile?.path), RenderableSource.SourceType.GLB)
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()
        ModelRenderable
            .builder()
            .setSource(this, renderableSource)
            .setRegistryId(viewModel.tempFile?.path)
            .build()
            .thenAccept { modelRenderable: ModelRenderable? ->
                if (modelRenderable != null) toast("Tải mô hình thành công")
                viewModel.model3d = modelRenderable
            }
    }
}
