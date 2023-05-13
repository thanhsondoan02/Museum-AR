package com.tiger.ar.museum.presentation.collection.all.az

import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.AzCollectionFragmentBinding
import com.tiger.ar.museum.presentation.collection.all.CollectionsAdapter
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class AZCollectionFragment : MuseumFragment<AzCollectionFragmentBinding>(R.layout.az_collection_fragment) {
    private val viewModel by viewModels<AzCollectionViewModel>()
    private val azAdapter by lazy { AZCollectionAdapter() }
    private val collectionAdapter by lazy { CollectionsAdapter() }

    override fun onInitView() {
        super.onInitView()
        initRecyclerViewLetter()
        initRecyclerViewCollection()
        viewModel.getCollectionStartWithLetter(
            viewModel.listLetterDisplay.first().letter,
            onSuccessAction = {
                binding.cvAzCollection.submitList(it)
            },
            onFailureAction = {
                toast(it)
            }
        )
    }

    private fun initRecyclerViewLetter() {
        azAdapter.listener = object : AZCollectionAdapter.IListener {
            override fun onLetterClick(letterDisplay: AZCollectionAdapter.LetterDisplay) {
                viewModel.getCollectionStartWithLetter(
                    letterDisplay.letter,
                    onSuccessAction = {
                        binding.cvAzCollection.submitList(it)
                    },
                    onFailureAction = {
                        toast(it)
                    }
                )
            }
        }

        binding.cvAzCollectionKey.apply {
            setAdapter(this@AZCollectionFragment.azAdapter)
            setLayoutManager(COLLECTION_MODE.HORIZONTAL)
            submitList(viewModel.listLetterDisplay)
        }
    }

    private fun initRecyclerViewCollection() {
        binding.cvAzCollection.apply {
            setAdapter(this@AZCollectionFragment.collectionAdapter)
            setLayoutManager(COLLECTION_MODE.GRID_VERTICAL)
            setMaxItemHorizontal(2)
        }
    }
}
