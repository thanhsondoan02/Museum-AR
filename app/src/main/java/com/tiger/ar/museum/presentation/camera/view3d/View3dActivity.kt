package com.tiger.ar.museum.presentation.camera.view3d

import android.view.MotionEvent
import androidx.activity.viewModels
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ux.ArFragment
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.common.extension.toastUndeveloped
import com.tiger.ar.museum.databinding.View3dActivityBinding
import com.tiger.ar.museum.presentation.camera.view3d.control.View3dControllerFragment

class View3dActivity : MuseumActivity<View3dActivityBinding>(R.layout.view_3d_activity) {
    private val viewModel by viewModels<View3dViewModel>()
    private var arFragment: ArFragment? = null

    override fun getContainerId() = R.id.flView3DFragmentContainer

    override fun onInitView() {
        super.onInitView()
        initArFragment()
        initOnClick()
        addFragment(View3dControllerFragment())
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initArFragment() {
        arFragment = supportFragmentManager.findFragmentById(R.id.fView3dArFragment) as? ArFragment
        arFragment?.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane?, motionEvent: MotionEvent? ->
            if (viewModel.modelRenderable != null) {
                val anchorNode = AnchorNode(hitResult.createAnchor())
                anchorNode.renderable = viewModel.modelRenderable
                arFragment!!.arSceneView.scene.addChild(anchorNode)
            } else {
                toast("Vui lòng chọn 1 mô hình")
            }
        }
    }

    private fun initOnClick() {
        binding.ivView3dControllerBack.setOnSafeClick {
            finish()
        }
        binding.ivView3dControllerMore.setOnSafeClick { toastUndeveloped() }
    }
}
