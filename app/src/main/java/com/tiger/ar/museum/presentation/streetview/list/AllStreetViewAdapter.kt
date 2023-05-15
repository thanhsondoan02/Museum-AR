package com.tiger.ar.museum.presentation.streetview.list

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.gone
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.HomeStreetViewSubItemBinding
import com.tiger.ar.museum.databinding.ItemListTitleItemBinding
import com.tiger.ar.museum.domain.model.StreetView

class AllStreetViewAdapter : MuseumAdapter() {
    companion object {
        const val TITLE_TYPE = 1410
        const val SV_TYPE = 1411
    }

    var listener: IListener? = null

    override fun getItemViewTypeCustom(position: Int): Int {
        return if (position == 0) {
            TITLE_TYPE
        } else {
            SV_TYPE
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            TITLE_TYPE -> R.layout.item_list_title_item
            SV_TYPE -> R.layout.home_street_view_sub_item
            else -> throw IllegalArgumentException("getLayoutResource: viewType is invalid")
        }
    }

    override fun setupEmptyState() = Empty(overrideLayoutRes = R.layout.empty_favorite_stories_item)

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            TITLE_TYPE -> TitleVH(binding as ItemListTitleItemBinding)
            SV_TYPE -> StreetViewVH(binding as HomeStreetViewSubItemBinding)
            else -> throw IllegalArgumentException("onCreateViewHolder: viewType is invalid")
        }
    }

    inner class TitleVH(private val binding: ItemListTitleItemBinding) : BaseVH<Int>(binding) {
        override fun onBind(data: Int) {
            binding.tvItemListTitle.text = getTitleText(data)
        }

        private fun getTitleText(data: Int): String {
            return getAppString(R.string.stories) + " - " + data
        }
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
                tvHomeStreetViewPlace.gone()
            }
        }
    }

    interface IListener {
        fun onStreetViewClick(streetView: StreetView)
    }
}
