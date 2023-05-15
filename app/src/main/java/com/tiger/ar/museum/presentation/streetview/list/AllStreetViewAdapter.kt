package com.tiger.ar.museum.presentation.streetview.list

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.gone
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.HomeStreetViewSubItem2Binding
import com.tiger.ar.museum.domain.model.StreetView

class AllStreetViewAdapter : MuseumAdapter() {
    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int): Int {
        return  R.layout.home_street_view_sub_item2
    }

    override fun setupEmptyState() = Empty(overrideLayoutRes = R.layout.empty_favorite_stories_item)

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return StreetViewVH(binding as HomeStreetViewSubItem2Binding)
    }

    inner class StreetViewVH(private val binding: HomeStreetViewSubItem2Binding) : BaseVH<StreetView>(binding) {
        init {
            binding.root.setOnSafeClick {
                getItem {
                    listener?.onStreetViewClick(it)
                }
            }
        }

        override fun onBind(data: StreetView) {
            binding.apply {
                ivHomeStreetViewThumbnail.loadImage(data.thumbnail)
                tvHomeStreetViewName.text = data.name
                tvHomeStreetViewPlace.gone()
            }
        }
    }

    interface IListener {
        fun onStreetViewClick(streetView: StreetView)
    }
}
