package com.tiger.ar.museum.presentation.search

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.gone
import com.tiger.ar.museum.common.extension.show
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.SearchActivityBinding
import com.tiger.ar.museum.presentation.RealMainActivity
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class SearchFragment : MuseumFragment<SearchActivityBinding>(R.layout.search_activity) {
    private val viewModel by viewModels<SearchViewModel>()
    private val searchKeyAdapter by lazy { SearchKeyAdapter() }
    private val searchAdapter by lazy { SearchAdapter() }
    private var oldEnable = false

    override fun onInitView() {
        super.onInitView()
        initOnClick()
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        if (museumActivity is RealMainActivity) {
            realMainActivity.setBackIcon()
            oldEnable = realMainActivity.enableScrollHideActionBar(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (oldEnable && museumActivity is RealMainActivity) {
            realMainActivity.enableScrollHideActionBar(true)
        }
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

        searchKeyAdapter.listener = object : SearchKeyAdapter.IListener {
            override fun onSearchKeyClick(text: String) {
                binding.cvSearchKey.gone()
                viewModel.getSearchData {
                    toast("Search $text")
                }
            }
        }

        binding.cvSearchKey.apply {
            setAdapter(this@SearchFragment.searchKeyAdapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }
    }
}
