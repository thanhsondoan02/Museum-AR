package com.tiger.ar.museum.presentation.explore

import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.ExploreFragmentBinding
import com.tiger.ar.museum.domain.model.Exhibition
import com.tiger.ar.museum.domain.model.Item
import com.tiger.ar.museum.presentation.RealMainActivity

class ExploreFragment: MuseumFragment<ExploreFragmentBinding>(R.layout.explore_fragment) {
    private val viewModel by viewModels<ExploreViewModel>()
    private val adapter by lazy { ExploreAdapter() }

    override fun onInitView() {
        super.onInitView()
        initViewPager2()
        viewModel.getExploreDataFromDatabase(
            onSuccessAction = {
                adapter.submitList(viewModel.getShuffledExploreData())
            },
            onFailureAction = {
                toast(getAppString(R.string.fail) + ": $it")
            }
        )
    }

    private fun initViewPager2() {
        adapter.listener = object : ExploreAdapter.IListener {
            override fun onLikeItem(item: Item) {
                viewModel.likeItem(item,
                    onSuccessAction = {
                        adapter.submitList(viewModel.exploreData)
                        (museumActivity as RealMainActivity).reloadFavorite()
                    },
                    onFailureAction = {
                        toast(getAppString(R.string.fail) + ": $it")
                    }
                )
            }

            override fun onBuyTicket(exhibition: Exhibition) {

            }

            override fun onZoomItem(item: Item) {

            }

            override fun onShareItem(item: Item) {

            }
        }

        binding.vp2ExploreExhibition.apply {
            adapter = this@ExploreFragment.adapter
            orientation = ViewPager2.ORIENTATION_VERTICAL
        }
    }
}
