package com.tiger.ar.museum.presentation.home

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.HomeStreetViewItemBinding
import com.tiger.ar.museum.domain.model.StreetView
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class HomeAdapter: MuseumAdapter() {
    companion object {
        const val STREET_VIEW_TYPE = 1410
    }

    var listener: IListener? = null

    override fun getItemViewTypeCustom(position: Int): Int {
        return when (getDataAtPosition(position)) {
            is StreetViewDisplay -> STREET_VIEW_TYPE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            STREET_VIEW_TYPE -> R.layout.home_street_view_item
            else -> throw IllegalArgumentException("getLayoutResource: viewType is invalid")
        }
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            STREET_VIEW_TYPE -> StreetViewListVH(binding as HomeStreetViewItemBinding)
            else -> throw IllegalArgumentException("onCreateViewHolder: viewType is invalid")
        }
    }

    inner class StreetViewListVH(private val binding: HomeStreetViewItemBinding) : BaseVH<StreetViewDisplay>(binding) {
        private val adapter by lazy { StreetViewAdapter() }

        init {
            adapter.listener = object : StreetViewAdapter.IListener {
                override fun onStreetViewClick(streetView: StreetView) {
                    listener?.onStreetViewClick(streetView)
                }
            }
            binding.cvHomeStreetView.apply {
                setAdapter(this@StreetViewListVH.adapter)
                setLayoutManager(COLLECTION_MODE.HORIZONTAL)
            }
            binding.mcvHomeStreetViewViewAll.setOnSafeClick {
                listener?.onViewAllStreetViewClick()
            }
        }

        override fun onBind(data: StreetViewDisplay) {
            binding.cvHomeStreetView.submitList(data.streetViews)
        }
    }

    class StreetViewDisplay {
        var streetViews: List<StreetView>? = null
    }

    interface IListener {
        fun onStreetViewClick(streetView: StreetView)
        fun onViewAllStreetViewClick()
    }
}
