package com.tiger.ar.museum.presentation.favorite.item

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.databinding.ItemListFragmentBinding
import com.tiger.ar.museum.domain.model.Item
import com.tiger.ar.museum.presentation.RealMainActivity
import com.tiger.ar.museum.presentation.item.ItemFragment
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class ItemListFragment : MuseumFragment<ItemListFragmentBinding>(R.layout.item_list_fragment) {
    companion object {
        const val ITEMS_KEY = "ITEMS_KEY"
    }

    private val adapter by lazy { ItemListAdapter() }
    private val viewModel by viewModels<ItemListViewModel>()

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        viewModel.items = arguments?.getParcelableArrayList(ITEMS_KEY) ?: emptyList()
    }

    override fun onInitView() {
        super.onInitView()
        (museumActivity as? RealMainActivity)?.apply {
            setBackIcon()
            expandAppBar()
        }

        adapter.listener = object : ItemListAdapter.IListener {
            override fun onItemClick(item: Item?) {
                museumActivity.addFragmentNew(
                    ItemFragment(),
                    bundleOf(ItemFragment.ITEM_ID_KEY to item?.key),
                    containerId = R.id.flRealMainContainer
                )
            }
        }

        binding.cvItemList.apply {
            setAdapter(this@ItemListFragment.adapter)
            setLayoutManager(COLLECTION_MODE.PESWOC)
        }
        viewModel.calculateSizeOfListImage {
            val tempList = mutableListOf<Any>()
            tempList.add(AppPreferences.getUserInfo().fitems?.size ?: 0)
            tempList.addAll(viewModel.itemDisplays)
            binding.cvItemList.submitList(tempList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (museumActivity as? RealMainActivity)?.apply {
            disableFragmentContainerScrollingBehavior()
        }
    }
}
