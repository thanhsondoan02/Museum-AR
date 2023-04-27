package com.tiger.ar.museum.presentation.favorite

import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.FavoriteMainFragmentBinding
import com.tiger.ar.museum.domain.model.Gallery
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class FavoriteMainFragment : MuseumFragment<FavoriteMainFragmentBinding>(R.layout.favorite_main_fragment) {
    private val viewModel by viewModels<FavoriteViewModel>()
    private val adapter: FavoriteAdapter by lazy { FavoriteAdapter() }

    override fun onInitView() {
        super.onInitView()
        initOnClick()
        initRecyclerView()
    }

    private fun initOnClick() {
    }

    private fun initRecyclerView() {
        adapter.listener = object : FavoriteAdapter.IListener {
            override fun onFavoriteTab() {
                binding.cvFavoriteMain.submitList(viewModel.listFavorite)
                viewModel.getFavoriteData(
                    onFailureAction = {
                        toast(getAppString(R.string.fail) + ": $it")
                    }
                )
            }

            override fun onGalleriesTab() {
                binding.cvFavoriteMain.submitList(viewModel.listGallery)
            }

            override fun onViewAllItem() {

            }

            override fun onViewAllStory() {

            }

            override fun onViewAllCollection() {

            }

            override fun onMoreGallery(gallery: Gallery) {

            }
        }
        binding.cvFavoriteMain.apply {
            setAdapter(this@FavoriteMainFragment.adapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
            submitList(viewModel.listFavorite)
        }
    }
}
