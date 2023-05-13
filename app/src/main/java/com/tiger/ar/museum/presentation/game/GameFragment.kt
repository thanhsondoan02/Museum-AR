package com.tiger.ar.museum.presentation.game

import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.databinding.CollectionsEmptyItemBinding

class GameFragment: MuseumFragment<CollectionsEmptyItemBinding>(R.layout.collections_empty_item) {
    override fun onInitView() {
        super.onInitView()
        binding.tvCollectionsEmpty.text = getAppString(R.string.undeveloped)
    }
}
