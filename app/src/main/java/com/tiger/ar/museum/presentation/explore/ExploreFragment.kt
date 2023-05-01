package com.tiger.ar.museum.presentation.explore

import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.ExploreFragmentBinding

class ExploreFragment: MuseumFragment<ExploreFragmentBinding>(R.layout.explore_fragment) {
    private val viewModel by viewModels<ExploreViewModel>()
    private val adapter by lazy { ExploreAdapter() }

    override fun onInitView() {
        super.onInitView()
        initViewPager2()
        viewModel.getExhibitionData(
            onSuccessAction = {
                adapter.submitList(viewModel.getExploreData())
            },
            onFailureAction = {
                toast(getAppString(R.string.fail) + ": $it")
            }
        )
    }

    private fun initViewPager2() {
        binding.vp2ExploreExhibition.apply {
            adapter = this@ExploreFragment.adapter
            orientation = ViewPager2.ORIENTATION_VERTICAL
        }
    }
}
