package com.tiger.ar.museum.presentation.camera.view3d

import androidx.activity.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.IViewListener
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.extension.coroutinesLaunch
import com.tiger.ar.museum.common.extension.handleUiState
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.View3dActivityBinding
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class View3dActivity: MuseumActivity<View3dActivityBinding>(R.layout.view_3d_activity) {
    private val viewModel by viewModels<View3dViewModel>()
    private val adapter by lazy { View3dAdapter() }

    override fun onInitView() {
        super.onInitView()
        initRecyclerView()
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
        binding.cvView3d.apply {
            setAdapter(this@View3dActivity.adapter)
            setLayoutManager(COLLECTION_MODE.HORIZONTAL)
        }
    }
}
