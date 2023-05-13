package com.tiger.ar.museum.presentation.favorite.collection

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.databinding.FavoriteCollectionsFragmentBinding
import com.tiger.ar.museum.domain.model.MCollection
import com.tiger.ar.museum.presentation.RealMainActivity
import com.tiger.ar.museum.presentation.collection.CollectionFragment
import com.tiger.ar.museum.presentation.collection.all.CollectionsAdapter
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class FavoriteCollectionsFragment : MuseumFragment<FavoriteCollectionsFragmentBinding>(R.layout.favorite_collections_fragment) {
    companion object {
        const val COLLECTION_LIST_KEY = "ITEMS_KEY"
    }

    private val adapter by lazy { FavoriteCollectionAdapter() }
    private val viewModel by viewModels<FavoriteCollectionsViewModel>()

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        viewModel.collections = arguments?.getParcelableArrayList(COLLECTION_LIST_KEY) ?: emptyList()
    }

    override fun onInitView() {
        super.onInitView()
        (museumActivity as? RealMainActivity)?.apply {
            setBackIcon()
            expandAppBar()
        }

        adapter.listener = object : CollectionsAdapter.IListener {
            override fun onCollectionClick(collection: MCollection) {
                museumActivity.addFragmentNew(
                    CollectionFragment(),
                    bundleOf(CollectionFragment.COLLECTION_ID_KEY to collection.key),
                    containerId = R.id.flRealMainContainer
                )
            }
        }

        binding.cvFavoriteCollections.apply {
            setAdapter(this@FavoriteCollectionsFragment.adapter)
            setLayoutManager(COLLECTION_MODE.GRID_VERTICAL)
            setMaxItemHorizontal(2)

            val newList = mutableListOf<Any>()
            newList.add(viewModel.collections.size)
            newList.addAll(viewModel.collections)
            submitList(newList)
        }
    }
}
