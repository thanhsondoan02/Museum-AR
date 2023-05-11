package com.tiger.ar.museum.presentation.home

import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.toastUndeveloped
import com.tiger.ar.museum.databinding.HomeFragmentBinding
import com.tiger.ar.museum.domain.model.StreetView
import com.tiger.ar.museum.presentation.streetview.StreetViewFragment
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class HomeFragment : MuseumFragment<HomeFragmentBinding>(R.layout.home_fragment) {
    private val adapter by lazy { HomeAdapter() }
    private val viewModel by viewModels<HomeViewModel>()

    override fun onInitView() {
        super.onInitView()
        initRecyclerView()
        viewModel.getHomeData {
            binding.cvHome.submitList(viewModel.list)
        }
    }

    private fun initRecyclerView() {
        adapter.listener = object : HomeAdapter.IListener {
            override fun onStreetViewClick(streetView: StreetView) {
                museumActivity.addFragmentNew(
                    StreetViewFragment().apply { this.location = streetView.location },
//                    bundleOf(StreetViewFragment.GEO_POINT_KEY to streetView.location),
                    containerId = R.id.flRealMainContainer
                )
            }

            override fun onViewAllStreetViewClick() {
                toastUndeveloped()
            }
        }

        binding.cvHome.apply {
            setAdapter(this@HomeFragment.adapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }
    }
}
