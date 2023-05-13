package com.tiger.ar.museum.presentation.collection

import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.common.extension.toastUndeveloped
import com.tiger.ar.museum.databinding.CollectionFragmentBinding
import com.tiger.ar.museum.presentation.RealMainActivity
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class CollectionFragment : MuseumFragment<CollectionFragmentBinding>(R.layout.collection_fragment) {
    companion object {
        const val COLLECTION_ID_KEY = "COLLECTION_ID_KEY"
    }

    private val viewModel by viewModels<CollectionViewModel>()
    private val adapter: CollectionAdapter by lazy { CollectionAdapter() }

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        viewModel.collectionId = arguments?.getString(COLLECTION_ID_KEY)
    }

    override fun onInitView() {
        super.onInitView()
        (museumActivity as RealMainActivity).apply {
            setBackIcon()
            setTransparentActionBar()
        }
        initOnClick()
        initRecyclerView()
        getCollectionData()
    }

    override fun onDestroy() {
        super.onDestroy()
        (museumActivity as RealMainActivity).apply {
            setWhiteActionBar()
        }
    }

    private fun initOnClick() {

    }

    private fun initRecyclerView() {
        adapter.listener = object : CollectionAdapter.IListener {
            override fun onItemClick(itemId: String) {

            }

            override fun onStoryClick(storyId: String) {

            }

            override fun onFollowClick() {
                viewModel.follow(
                    onSuccessAction = {
                        binding.cvCollection.submitList(viewModel.list)
                        toast("Follow success")
                    },
                    onFailureAction = {
                        toast("Fail: $it")
                    }
                )
            }

            override fun onUnFollowClick() {
                viewModel.unFollow(
                    onSuccessAction = {
                        binding.cvCollection.submitList(viewModel.list)
                        toast("Unfollow success")
                    },
                    onFailureAction = {
                        toast("Fail: $it")
                    }
                )
            }

            override fun onCollectionTabClick() {

            }

            override fun onVisitTabClick() {

            }

            override fun onShareClick() {
                toastUndeveloped()
            }
        }
        binding.cvCollection.apply {
            setAdapter(this@CollectionFragment.adapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }
    }

    private fun getCollectionData() {
        viewModel.getCollectionData(
            onSuccessAction = {
                binding.cvCollection.submitList(viewModel.list)
            },
            onFailureAction = {
                toast("Fail: $it")
            }
        )
    }
}
