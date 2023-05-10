package com.tiger.ar.museum.presentation.item

import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.databinding.ItemFragmentBinding
import com.tiger.ar.museum.presentation.RealMainActivity
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class ItemFragment : MuseumFragment<ItemFragmentBinding>(R.layout.item_fragment) {
    companion object {
        const val ITEM_KEY = "ITEMS_KEY"
    }

    private val adapter by lazy { ItemAdapter() }
    private val actionAdapter by lazy { ItemActionAdapter() }
    private val viewModel by viewModels<ItemViewModel>()

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        viewModel.item = arguments?.getParcelable(ITEM_KEY)
    }

    override fun onInitView() {
        super.onInitView()
        (museumActivity as? RealMainActivity)?.apply {
            setBackIcon()
            enableScrollHideActionBar(false)
        }
        binding.ivItem.loadImage(viewModel.item?.thumbnail)
        initAction()
        initBackDrop()
    }

    override fun onBackPressByFragment() {
        (museumActivity as? RealMainActivity)?.apply {
            enableScrollHideActionBar(true)
        }
    }

    private fun initAction() {
        actionAdapter.listener = object : ItemActionAdapter.IListener {
            override fun onActionClick(actionType: ACTION_TYPE) {
                when (actionType) {
                    ACTION_TYPE.ZOOM_IN -> {

                    }
                    ACTION_TYPE.AR -> {

                    }
                    ACTION_TYPE.STREET -> {

                    }
                }
            }
        }

        binding.cvItem.apply {
            setAdapter(this@ItemFragment.actionAdapter)
            setLayoutManager(COLLECTION_MODE.HORIZONTAL)
            submitList(listOf(
                ItemActionAdapter.ActionDisplay(ACTION_TYPE.ZOOM_IN),
                ItemActionAdapter.ActionDisplay(ACTION_TYPE.AR),
                ItemActionAdapter.ActionDisplay(ACTION_TYPE.STREET),
            ))
        }
    }

    private fun initBackDrop() {
//        binding.cvItem.apply {
//            setAdapter(this@ItemFragment.adapter)
//            setLayoutManager(COLLECTION_MODE.PESWOC)
//        }
//        viewModel.mapDataForAdapter {
//            binding.cvItem.submitList(viewModel.itemData)
//        }
    }
}
