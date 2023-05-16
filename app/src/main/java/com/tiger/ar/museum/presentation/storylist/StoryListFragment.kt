package com.tiger.ar.museum.presentation.storylist

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.databinding.ItemListFragmentBinding
import com.tiger.ar.museum.presentation.RealMainActivity
import com.tiger.ar.museum.presentation.collection.CollectionFragment
import com.tiger.ar.museum.presentation.story.StoryFragment
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class StoryListFragment : MuseumFragment<ItemListFragmentBinding>(R.layout.item_list_fragment) {
    companion object {
        const val STORIES_KEY = "ITEMS_KEY"
    }

    private val adapter by lazy { StoryListAdapter() }
    private val viewModel by viewModels<StoryListViewModel>()

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        viewModel.streetViews = arguments?.getParcelableArrayList(STORIES_KEY) ?: emptyList()
    }

    override fun onInitView() {
        super.onInitView()
        (museumActivity as? RealMainActivity)?.apply {
            setBackIcon()
            expandAppBar()
            enableFragmentContainerScrollingBehavior()
            setWhiteActionBar()
        }

        adapter.listener = object : StoryListAdapter.IListener {
            override fun onStoryClick(storyId: String?) {
                addFragmentNew(
                    StoryFragment(),
                    bundleOf(StoryFragment.STORY_ID_KEY to storyId),
                    containerId = R.id.flRealMainContainer
                )
            }
        }

        binding.cvItemList.apply {
            setAdapter(this@StoryListFragment.adapter)
            setLayoutManager(COLLECTION_MODE.GRID_VERTICAL)
            setMaxItemHorizontal(2)

            val list = mutableListOf<Any>()
            list.add(viewModel.streetViews.size)
            list.addAll(viewModel.streetViews)
            submitList(list)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (museumActivity as? RealMainActivity)?.apply {
            disableFragmentContainerScrollingBehavior()
        }
        if (museumActivity.supportFragmentManager.fragments.lastOrNull() is CollectionFragment) {
            realMainActivity.setTransparentActionBar()
            realMainActivity.disableFragmentContainerScrollingBehavior()
        }
    }
}
