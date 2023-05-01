package com.tiger.ar.museum.presentation.favorite.item

import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.databinding.ItemListFragmentBinding
import com.tiger.ar.museum.domain.model.Item
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class ItemListFragment: MuseumFragment<ItemListFragmentBinding>(R.layout.item_list_fragment) {
    companion object {
        const val ITEMS_KEY = "ITEMS_KEY"
    }

    var items: List<Item> = emptyList()
    private val adapter by lazy { ItemListAdapter() }

    override fun onInitView() {
        super.onInitView()
        binding.cvItemList.apply {
            setAdapter(this@ItemListFragment.adapter)
            setLayoutManager(COLLECTION_MODE.GRID_VERTICAL)
            setMaxItemHorizontal(2)
            submitList(items)
        }
    }

}
