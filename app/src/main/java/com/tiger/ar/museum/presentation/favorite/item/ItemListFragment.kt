package com.tiger.ar.museum.presentation.favorite.item

import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.databinding.ItemListFragmentBinding
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class ItemListFragment: MuseumFragment<ItemListFragmentBinding>(R.layout.item_list_fragment) {
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
        binding.cvItemList.apply {
            setAdapter(this@ItemListFragment.adapter)
            setLayoutManager(COLLECTION_MODE.PESWOC)
//            setMaxItemHorizontal(2)
        }
        viewModel.calculateSizeOfListImage {
            binding.cvItemList.submitList(viewModel.itemDisplays)
        }
    }
}
