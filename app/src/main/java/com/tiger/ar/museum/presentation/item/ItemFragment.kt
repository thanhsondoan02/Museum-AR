package com.tiger.ar.museum.presentation.item

import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.ItemFragmentBinding
import com.tiger.ar.museum.presentation.RealMainActivity
import com.tiger.ar.museum.presentation.favorite.item.ItemListFragment
import com.tiger.ar.museum.presentation.streetview.StreetViewFragment
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class ItemFragment : MuseumFragment<ItemFragmentBinding>(R.layout.item_fragment) {
    companion object {
        const val ITEM_KEY = "ITEMS_KEY"
    }

    private val adapter by lazy { ItemAdapter() }
    private val viewModel by viewModels<ItemViewModel>()

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        viewModel.itemId = arguments?.getString(ITEM_KEY)
    }

    override fun onInitView() {
        super.onInitView()
        (museumActivity as? RealMainActivity)?.apply {
            setBackIcon()
            enableScrollHideActionBar(false)
        }
        initAction()
        initBackDrop()
        viewModel.getItemData(
            onSuccessAction = {
                binding.ivItem.loadImage(viewModel.item?.thumbnail)
                binding.cvItemBackDrop.submitList(viewModel.itemData)
            },
            onFailureAction = {
                toast("Get item data fail: $it")
            }
        )
    }

    override fun onBackPressByFragment() {
        (museumActivity as? RealMainActivity)?.apply {
            enableScrollHideActionBar(true)
        }
    }

    private fun initAction() {
        adapter.listener = object : ItemAdapter.IListener {
            override fun onActionClick(actionType: ACTION_TYPE) {
                when (actionType) {
                    ACTION_TYPE.ZOOM_IN -> {

                    }

                    ACTION_TYPE.AR -> {

                    }

                    ACTION_TYPE.STREET -> {
                        museumActivity.addFragmentNew(
                            StreetViewFragment().apply { this.location = viewModel.item?.streetView },
                            containerId = R.id.flRealMainContainer
                        )
                    }
                }
            }

            override fun onDetailTitleClick(isOpen: Boolean) {
//                TODO("Not yet implemented")
            }

            override fun onLikeClick() {
                viewModel.likeUpdate(
                    true,
                    onSuccessAction = {
                        binding.cvItemBackDrop.submitList(viewModel.itemData)
                        realMainActivity.reloadFavorite()
                    },
                    onFailureAction = {
                        toast("Like fail: $it")
                    }
                )
            }

            override fun onDislikeClick() {
                viewModel.likeUpdate(
                    false,
                    onSuccessAction = {
                        binding.cvItemBackDrop.submitList(viewModel.itemData)
                        (museumActivity as RealMainActivity).reloadFavorite()
                    },
                    onFailureAction = {
                        toast("Like fail: $it")
                    }
                )
            }

            override fun onShareClick() {

            }
        }
    }

    private fun initBackDrop() {
        binding.cvItemBackDrop.apply {
            setAdapter(this@ItemFragment.adapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (museumActivity.supportFragmentManager.fragments.lastOrNull() is ItemListFragment) {
            (museumActivity as? RealMainActivity)?.enableFragmentContainerScrollingBehavior()
        }
    }
}
