package com.tiger.ar.museum.presentation.story

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.StoryItemBinding
import com.tiger.ar.museum.domain.model.Page

class StoryAdapter: MuseumAdapter() {
    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.story_item
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return StoryVH(binding as StoryItemBinding)
    }

    inner class StoryVH(private val binding: StoryItemBinding): BaseVH<Page>(binding) {
        override fun onBind(data: Page) {
            binding.ivStory.loadImage(data.thumbnail)
            binding.tvStory.text = data.description
        }
    }
}
