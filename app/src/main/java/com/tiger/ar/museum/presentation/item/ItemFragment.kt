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
    private val viewModel by viewModels<ItemViewModel>()

    override fun onPrepareInitView() {
        super.onPrepareInitView()
//        viewModel.item = arguments?.getParcelable(ITEM_KEY)
    }

    override fun onInitView() {
        super.onInitView()
        (museumActivity as? RealMainActivity)?.apply {
            setBackIcon()
            enableScrollHideActionBar(false)
        }
        initImage()
        initAction()
        initBackDrop()
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

                    }
                }
            }

            override fun onDetailTitleClick(isOpen: Boolean) {
//                TODO("Not yet implemented")
            }

            override fun onLikeClick() {
//                TODO("Not yet implemented")
            }

            override fun onDislikeClick() {
//                TODO("Not yet implemented")
            }

            override fun onShareClick() {
//                TODO("Not yet implemented")
            }
        }
    }

    private fun initBackDrop() {
        binding.cvItemBackDrop.apply {
            setAdapter(this@ItemFragment.adapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }
        viewModel.mapDataForAdapter {
            binding.cvItemBackDrop.submitList(viewModel.itemData)
        }
    }

    private fun initImage() {
        binding.ivItem.loadImage(viewModel.item?.thumbnail)
    }
}
