package com.tiger.ar.museum.presentation.collection.all.all

import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.AllCollectionFragmentBinding
import com.tiger.ar.museum.presentation.collection.all.CollectionsAdapter
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class AllCollectionFragment: MuseumFragment<AllCollectionFragmentBinding>(R.layout.all_collection_fragment) {
    private val viewModel by viewModels<AllCollectionViewModel>()
    private val adapter by lazy { CollectionsAdapter() }

    override fun onInitView() {
        super.onInitView()
        initRecyclerView()
        viewModel.getListCollection(
            onSuccessAction = {
                binding.cvAllCollectionTabAll.submitList(it)
            },
            onFailureAction = {
                toast(it)
            }
        )
    }

    private fun initRecyclerView() {
        binding.cvAllCollectionTabAll.apply {
            setAdapter(this@AllCollectionFragment.adapter)
            setLayoutManager(COLLECTION_MODE.GRID_VERTICAL)
            setMaxItemHorizontal(2)
        }
    }
}
