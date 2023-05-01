package com.tiger.ar.museum.presentation.explore

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.extension.loadImageBlur
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.ExploreExhibitionItemBinding
import com.tiger.ar.museum.domain.model.Exhibition

class ExploreAdapter : MuseumAdapter() {
    companion object {
        const val EXHIBITION_TYPE = 2002
    }

    override fun getItemViewTypeCustom(position: Int): Int {
        return when (getDataAtPosition(position)) {
            is Exhibition -> EXHIBITION_TYPE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            EXHIBITION_TYPE -> R.layout.explore_exhibition_item
            else -> INVALID_RESOURCE
        }
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            EXHIBITION_TYPE -> ExhibitionVH(binding as ExploreExhibitionItemBinding)
            else -> null
        }
    }

    inner class ExhibitionVH(private val binding: ExploreExhibitionItemBinding) : BaseVH<Exhibition>(binding) {
        override fun onBind(data: Exhibition) {
            binding.ivExploreExhibitionThumb.loadImage(data.thumbnail)
            binding.ivExploreExhibitionMuseumThumb.loadImage(data.museumThumb)
            binding.tvExploreExhibitionName.text = data.name
            binding.tvExploreExhibitionMuseumInfo.text = data.getMuseumInfo()
            binding.tvExploreExhibitionDate.text = data.getExhibitionDate()
            binding.tvExploreExhibitionTitle.text = data.getExhibitionTitle()
            binding.ivExploreExhibitionBackground.loadImageBlur(data.thumbnail, radius = 100, sampling = 10)

        }
    }
}
