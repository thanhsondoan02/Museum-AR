package com.tiger.ar.museum.presentation.favorite

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.FavoriteMainFragmentBinding
import com.tiger.ar.museum.domain.model.Gallery
import com.tiger.ar.museum.presentation.collection.CollectionFragment
import com.tiger.ar.museum.presentation.favorite.item.ItemListFragment
import com.tiger.ar.museum.presentation.item.ItemFragment
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class FavoriteMainFragment : MuseumFragment<FavoriteMainFragmentBinding>(R.layout.favorite_main_fragment) {
    private val viewModel by viewModels<FavoriteViewModel2>()
    private val adapter: FavoriteAdapter by lazy { FavoriteAdapter() }

    override fun onInitView() {
        super.onInitView()
        initOnClick()
        initRecyclerView()
        getFavoriteData()
    }

    fun getFavoriteData() {
        viewModel.getFavoriteData(
            onSuccessAction = {
                if (viewModel.isFavorite) {
                    binding.cvFavoriteMain.submitList(viewModel.getShortListFavorite())
                } else {
                    binding.cvFavoriteMain.submitList(viewModel.getShortListGallery())
                }
            },
            onFailureAction = {
                toast(getAppString(R.string.fail) + ": $it")
            }
        )
    }

    private fun initOnClick() {
    }

    private fun initRecyclerView() {
        adapter.listener = object : FavoriteAdapter.IListener {
            override fun onFavoriteTab() {
                viewModel.isFavorite = true
                binding.cvFavoriteMain.submitList(viewModel.getShortListFavorite())
            }

            override fun onGalleriesTab() {
                viewModel.isFavorite = false
                binding.cvFavoriteMain.submitList(viewModel.getShortListGallery())
            }

            override fun onViewAllItem() {
                museumActivity.addFragmentNew(
                    ItemListFragment(),
                    bundleOf(ItemListFragment.ITEMS_KEY to viewModel.items),
                    containerId = R.id.flRealMainContainerWithScrollBehavior
                )
            }

            override fun onViewAllStory() {

            }

            override fun onViewAllCollection() {

            }

            override fun onMoreGallery(gallery: Gallery) {

            }

            override fun onItemClick(itemId: String?) {
                museumActivity.addFragmentNew(
                    ItemFragment(),
                    bundleOf(ItemFragment.ITEM_KEY to itemId),
                    containerId = R.id.flRealMainContainer
                )
            }

            override fun onStoryClick(storyId: String?) {

            }

            override fun onCollectionClick(collectionId: String?) {
                museumActivity.addFragmentNew(
                    CollectionFragment(),
                    bundleOf(CollectionFragment.COLLECTION_ID_KEY to collectionId),
                    containerId = R.id.flRealMainContainer
                )
            }
        }
        binding.cvFavoriteMain.apply {
            setAdapter(this@FavoriteMainFragment.adapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
            submitList(viewModel.getShortListFavorite())
        }
    }
}
