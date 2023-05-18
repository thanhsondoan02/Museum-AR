package com.tiger.ar.museum.presentation.search

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.show
import com.tiger.ar.museum.databinding.SearchActivityBinding
import com.tiger.ar.museum.presentation.RealMainActivity
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class SearchFragment : MuseumFragment<SearchActivityBinding>(R.layout.search_activity) {
    private val viewModel by viewModels<SearchViewModel>()
    private val searchKeyAdapter by lazy { SearchKeyAdapter() }
    private val searchAdapter by lazy { SearchAdapter() }

    override fun onInitView() {
        super.onInitView()
        if (museumActivity is RealMainActivity) {
            realMainActivity.setBackIcon()
        }
        initOnClick()
        initRecyclerView()
    }

    private fun initOnClick() {
        if (museumActivity is RealMainActivity) {
            realMainActivity.getEditText().doAfterTextChanged {
                viewModel.getSuggestText(it.toString()) {
                    binding.cvSearchKey.show()
                    searchKeyAdapter.submitList(viewModel.suggestTexts)
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.cvSearch.apply {
            setAdapter(this@SearchFragment.searchAdapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }

        binding.cvSearchKey.apply {
            setAdapter(this@SearchFragment.searchKeyAdapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }
    }
}
