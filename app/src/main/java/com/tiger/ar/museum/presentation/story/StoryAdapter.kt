package com.tiger.ar.museum.presentation.story

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.gone
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.extension.show
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.StoryItemBinding

class StoryAdapter: MuseumAdapter() {
    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.story_item
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return StoryVH(binding as StoryItemBinding)
    }

    inner class StoryVH(private val binding: StoryItemBinding): BaseVH<PageDisplay>(binding) {
        init {
            binding.vStoryLeft.setOnSafeClick { listener?.onLeftClick() }
            binding.vStoryRight.setOnSafeClick { listener?.onRightClick() }
        }

        override fun onBind(data: PageDisplay) {
            binding.ivStory.loadImage(data.thumbnail)
            binding.tvStory.text = data.desc
            if (data.title != null) {
                binding.tvStoryTitle.apply {
                    show()
                    text = data.title
                }
                binding.flStoryNext.show()
            } else {
                binding.tvStoryTitle.gone()
                binding.flStoryNext.gone()
            }
        }
    }

    data class PageDisplay(
        var title: String? = null,
        var desc: String? = null,
        var thumbnail: String? = null
    )

    interface IListener {
        fun onRightClick()
        fun onLeftClick()
    }
}
