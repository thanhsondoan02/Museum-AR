package com.tiger.ar.museum.presentation.explore

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.common.extension.toastUndeveloped
import com.tiger.ar.museum.databinding.ExploreFragmentBinding
import com.tiger.ar.museum.domain.model.Exhibition
import com.tiger.ar.museum.domain.model.Item
import com.tiger.ar.museum.presentation.RealMainActivity
import com.tiger.ar.museum.presentation.ZoomFragment
import com.tiger.ar.museum.presentation.collection.CollectionFragment
import com.tiger.ar.museum.presentation.item.ItemFragment

class ExploreFragment : MuseumFragment<ExploreFragmentBinding>(R.layout.explore_fragment) {
    private val viewModel by viewModels<ExploreViewModel>()
    private val adapter by lazy { ExploreAdapter() }

    override fun onInitView() {
        super.onInitView()
        initViewPager2()
        initTopAppBarCallBack()
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
                addFragmentNew(
                    ZoomFragment(),
                    bundleOf(ZoomFragment.IMAGE_URL_KEY to item.thumbnail),
                    containerId = R.id.flRealMainContainer
                )
            }

            override fun onShareItem(item: Item) {
                toastUndeveloped()
            }

            override fun onViewItem(itemId: String?) {
                addFragmentNew(
                    ItemFragment(),
                    bundleOf(ItemFragment.ITEM_ID_KEY to itemId),
                    containerId = R.id.flRealMainContainer
                )
            }

            override fun onViewCollection(collectionId: String?) {
                addFragmentNew(
                    CollectionFragment(),
                    bundleOf(CollectionFragment.COLLECTION_ID_KEY to collectionId),
                    containerId = R.id.flRealMainContainer
                )
            }
        }

        binding.vp2ExploreExhibition.apply {
            adapter = this@ExploreFragment.adapter
            orientation = ViewPager2.ORIENTATION_VERTICAL
        }
    }

    private fun initTopAppBarCallBack() {
//        (museumActivity as RealMainActivity).addCallBackOnOffSetChange { appbarLayOut, verticalOffset ->
//            val params = binding.vp2ExploreExhibition.layoutParams as ViewGroup.MarginLayoutParams
//            params.topMargin = appbarLayOut.height + verticalOffset
//            binding.vp2ExploreExhibition.layoutParams = params
//        }
    }

    private fun abs(a: Int): Int {
        return if (a < 0) -a else a
    }
}
