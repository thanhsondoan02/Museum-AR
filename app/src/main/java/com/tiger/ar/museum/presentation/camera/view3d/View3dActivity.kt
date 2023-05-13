package com.tiger.ar.museum.presentation.camera.view3d

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.view.MotionEvent
import androidx.activity.viewModels
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.View3dActivityBinding
import com.tiger.ar.museum.domain.model.Model3d
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class View3dActivity : MuseumActivity<View3dActivityBinding>(R.layout.view_3d_activity) {
    private val viewModel by viewModels<View3dViewModel>()
    private val adapter by lazy { View3dAdapter() }
    private var arFragment: ArFragment? = null
    private val downloadReceiver = DownloadReceiver()

    override fun onInitView() {
        super.onInitView()
        initRecyclerView()
        initArFragment()

        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        registerReceiver(downloadReceiver, filter)

        viewModel.getFileFromUrl2(
            onStartAction = {
                toast("Bắt đầu tải xuống")
            }, onSuccessAction = {
                toast("Download thành công")
            }, onFailureAction = {
                toast("Download thất bại: $it")
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
    }

    private fun initRecyclerView() {
        adapter.listener = object : View3dAdapter.IListener {
            override fun onDownloadClick(model3d: Model3d) {
            }
        }
        binding.cvView3d.apply {
            setAdapter(this@View3dActivity.adapter)
            setLayoutManager(COLLECTION_MODE.HORIZONTAL)
        }
    }

    private fun initArFragment() {
        arFragment = supportFragmentManager.findFragmentById(R.id.fView3dArFragment) as? ArFragment
        arFragment?.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane?, motionEvent: MotionEvent? ->
            val anchorNode = AnchorNode(hitResult.createAnchor())
            anchorNode.renderable = viewModel.model3d
            arFragment!!.arSceneView.scene.addChild(anchorNode)
        }
    }

    private fun buildModel() {
        toast("Bắt đầu render mô hình")
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
                if (modelRenderable != null) {
                    toast("Render mô hình thành công")
                    viewModel.model3d = modelRenderable
                }
            }
    }

    inner class DownloadReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (downloadId != -1L) {
                    buildModel()
                    toast("Tải mô hình thành công")
                }
            }
        }
    }
}
