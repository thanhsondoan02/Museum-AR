package com.tiger.ar.museum.presentation

import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.BaseBindingFragment
import com.tiger.ar.museum.databinding.CollectionFragmentBinding

class CollectionFragment : BaseBindingFragment<CollectionFragmentBinding>(R.layout.collection_fragment) {
    private val adapter: CollectionAdapter by lazy { CollectionAdapter() }

    override fun onInitView() {
        super.onInitView()
        binding.cvCollection.apply {
            setAdapter(this@CollectionFragment.adapter)
        }
    }
}
