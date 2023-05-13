package com.tiger.ar.museum.presentation.camera.view3d.control

import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.common.extension.toastUndeveloped
import com.tiger.ar.museum.databinding.View3dControllerFragmentBinding
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class View3dControllerFragment: MuseumFragment<View3dControllerFragmentBinding>(R.layout.view_3d_controller_fragment) {
    private val adapter by lazy { View3dControllerAdapter() }
    private val viewModel by viewModels<View3dControllerViewModel>()

    var listener: IListener? = null

    override fun onInitView() {
        super.onInitView()
        initRecyclerView()
        initOnClick()
        viewModel.getListItem(
            onSuccessAction = {
                binding.cvView3d.submitList(viewModel.listItem)
            }, onFailAction = {
                toast("Fail: $it")
            }
        )
    }

    private fun initRecyclerView() {
        adapter.listener = object : View3dControllerAdapter.IListener {
            override fun onDownloadClick(itemDisplay: View3dControllerAdapter.ItemDisplay) {
                listener?.onDownloadClick(itemDisplay)
            }
        }
        binding.cvView3d.apply {
            setAdapter(this@View3dControllerFragment.adapter)
            setLayoutManager(COLLECTION_MODE.HORIZONTAL)
        }
    }

    private fun initOnClick() {
        binding.ivView3dControllerBack.setOnSafeClick { navigateBack() }
        binding.ivView3dControllerMore.setOnSafeClick { toastUndeveloped() }
    }

    interface IListener {
        fun onDownloadClick(itemDisplay: View3dControllerAdapter.ItemDisplay)
    }
}
