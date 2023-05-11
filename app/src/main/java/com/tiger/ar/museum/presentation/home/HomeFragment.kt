package com.tiger.ar.museum.presentation.home

import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.databinding.HomeFragmentBinding
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class HomeFragment: MuseumFragment<HomeFragmentBinding>(R.layout.home_fragment) {
    private val adapter by lazy { HomeAdapter() }
    private val viewModel by viewModels<HomeViewModel>()

    override fun onInitView() {
        super.onInitView()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.cvHome.apply {
            setAdapter(this@HomeFragment.adapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }
    }
}
