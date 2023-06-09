package com.tiger.ar.museum.presentation.favorite

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.FavoriteStoryChildItemBinding
import com.tiger.ar.museum.domain.model.Story

class FavoriteStoryAdapter : MuseumAdapter() {
    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int) = R.layout.favorite_story_child_item

    override fun setupEmptyState() = Empty(overrideLayoutRes = R.layout.empty_favorite_stories_item)

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return FavoriteStoryVH(binding as FavoriteStoryChildItemBinding)
    }

    inner class FavoriteStoryVH(private val binding: FavoriteStoryChildItemBinding) : BaseVH<Story>(binding) {
        init {
            binding.root.setOnClickListener {
                getItem {
                    listener?.onStoryClick(it.key)
                }
            }
        }

        override fun onBind(data: Story) {
            binding.ivFavoriteStoryChildThumbnail.loadImage(data.thumbnail)
            binding.tvFavoriteStoryChildTitle.text = data.title
            binding.tvFavoriteStoryChildDescription.text = data.description
        }
    }

    interface IListener {
        fun onStoryClick(storyId: String?)
    }
}
