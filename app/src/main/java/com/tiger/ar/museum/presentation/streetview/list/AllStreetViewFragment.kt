package com.tiger.ar.museum.presentation.streetview.list

import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.StreetViewListFragmentBinding
import com.tiger.ar.museum.domain.model.StreetView
import com.tiger.ar.museum.presentation.RealMainActivity
import com.tiger.ar.museum.presentation.streetview.item.StreetViewFragment
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class AllStreetViewFragment : MuseumFragment<StreetViewListFragmentBinding>(R.layout.street_view_list_fragment) {
    private val adapter by lazy { AllStreetViewAdapter() }
    private val viewModel by viewModels<AllStreetViewModel>()

    override fun onInitView() {
        super.onInitView()
        (museumActivity as? RealMainActivity)?.apply {
            setBackIcon()
            expandAppBar()
            enableFragmentContainerScrollingBehavior()
            setWhiteActionBar()
        }

        adapter.listener = object : AllStreetViewAdapter.IListener {
            override fun onStreetViewClick(streetView: StreetView) {
                museumActivity.addFragmentNew(
                    StreetViewFragment().apply { this.location = streetView.location },
                    containerId = R.id.flRealMainContainer
                )
            }
        }

        binding.cvAllStreetView.apply {
            setAdapter(this@AllStreetViewFragment.adapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }

        getData()
    }

    private fun getData() {
        viewModel.getAllStreetView(onSuccess = {
            binding.cvAllStreetView.submitList(viewModel.countAndStreetViews)
        }, onFailure = {
            toast("Failed to get street view list: $it")
        })
    }
}
