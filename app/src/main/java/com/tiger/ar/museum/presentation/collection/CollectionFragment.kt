package com.tiger.ar.museum.presentation.collection

import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.IViewListener
import com.tiger.ar.museum.common.binding.BaseBindingFragment
import com.tiger.ar.museum.common.extension.coroutinesLaunch
import com.tiger.ar.museum.common.extension.handleUiState
import com.tiger.ar.museum.common.extension.hideRefresh
import com.tiger.ar.museum.databinding.CollectionFragmentBinding
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class CollectionFragment : BaseBindingFragment<CollectionFragmentBinding>(R.layout.collection_fragment) {
    private val viewModel by viewModels<CollectionViewModel>()
    private val adapter: CollectionAdapter by lazy { CollectionAdapter() }

    override fun onInitView() {
        super.onInitView()
        binding.cvCollection.apply {
            setAdapter(this@CollectionFragment.adapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
            setMaxItemHorizontal(2)
            setLoadMoreListener { viewModel.getListCollection() }
            setCloseRefreshListener { binding.srlCollection.hideRefresh() }
        }
        binding.srlCollection.setOnRefreshListener { viewModel.getListCollection() }
        viewModel.getListCollection()
    }

    override fun onObserverViewModel() {
        super.onObserverViewModel()

        coroutinesLaunch(viewModel.get3dModelState) {
            handleUiState(it, object : IViewListener {
                override fun onLoading() {
                }

                override fun onFailure() {
                }

                override fun onSuccess() {
                    binding.cvCollection.submitList(it.data)
                }
            })
        }
    }
}
