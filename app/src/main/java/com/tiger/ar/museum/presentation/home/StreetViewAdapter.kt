package com.tiger.ar.museum.presentation.home

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.HomeStreetViewSubItemBinding
import com.tiger.ar.museum.domain.model.StreetView

class StreetViewAdapter: MuseumAdapter() {
    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int) = R.layout.home_street_view_sub_item

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return StreetViewVH(binding as HomeStreetViewSubItemBinding)
    }

    inner class StreetViewVH(private val binding: HomeStreetViewSubItemBinding) : BaseVH<StreetView>(binding) {
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
                tvHomeStreetViewPlace.text = data.place
            }
        }
    }

    interface IListener {
        fun onStreetViewClick(streetView: StreetView)
    }
}
