package com.tiger.ar.museum.presentation.home

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.recycleview.BaseVH
import com.tiger.ar.museum.common.recycleview.MuseumAdapter
import com.tiger.ar.museum.databinding.HomeCollectionItemBinding
import com.tiger.ar.museum.databinding.HomeStreetViewItemBinding
import com.tiger.ar.museum.domain.model.MCollection
import com.tiger.ar.museum.domain.model.StreetView
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class HomeAdapter: MuseumAdapter() {
    companion object {
        const val STREET_VIEW_TYPE = 1410
        const val COLLECTION_TYPE = 1411
    }

    var listener: IListener? = null

    override fun getItemViewTypeCustom(position: Int): Int {
        return when (getDataAtPosition(position)) {
            is StreetViewDisplay -> STREET_VIEW_TYPE
            is CollectionDisplay -> COLLECTION_TYPE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            STREET_VIEW_TYPE -> R.layout.home_street_view_item
            COLLECTION_TYPE -> R.layout.home_collection_item
            else -> throw IllegalArgumentException("getLayoutResource: viewType is invalid")
        }
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            STREET_VIEW_TYPE -> StreetViewListVH(binding as HomeStreetViewItemBinding)
            COLLECTION_TYPE -> CollectionListVH(binding as HomeCollectionItemBinding)
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

    inner class CollectionListVH(private val binding: HomeCollectionItemBinding) : BaseVH<CollectionDisplay>(binding) {
        init {
            binding.mcvHomeCollectionExploreAll.setOnSafeClick {
                listener?.onViewAllCollections()
            }
            binding.ivHomeCollection1.setOnSafeClick {
                getItem {
                    listener?.onCollectionClick(it.collections?.getOrNull(0)?.key)
                }
            }
            binding.ivHomeCollection2.setOnSafeClick {
                getItem {
                    listener?.onCollectionClick(it.collections?.getOrNull(1)?.key)
                }
            }
            binding.ivHomeCollection3.setOnSafeClick {
                getItem {
                    listener?.onCollectionClick(it.collections?.getOrNull(2)?.key)
                }
            }
            binding.ivHomeCollection4.setOnSafeClick {
                getItem {
                    listener?.onCollectionClick(it.collections?.getOrNull(3)?.key)
                }
            }
            binding.ivHomeCollection5.setOnSafeClick {
                getItem {
                    listener?.onCollectionClick(it.collections?.getOrNull(4)?.key)
                }
            }
        }

        override fun onBind(data: CollectionDisplay) {
            binding.ivHomeCollection1.loadImage(data.collections?.getOrNull(0)?.icon)
            binding.ivHomeCollection2.loadImage(data.collections?.getOrNull(1)?.icon)
            binding.ivHomeCollection3.loadImage(data.collections?.getOrNull(2)?.icon)
            binding.ivHomeCollection4.loadImage(data.collections?.getOrNull(3)?.icon)
            binding.ivHomeCollection5.loadImage(data.collections?.getOrNull(4)?.icon)
        }
    }

    class StreetViewDisplay {
        var streetViews: List<StreetView>? = null
    }

    class CollectionDisplay {
        var collections: List<MCollection>? = null
    }

    interface IListener {
        fun onStreetViewClick(streetView: StreetView)
        fun onViewAllStreetViewClick()
        fun onViewAllCollections()
        fun onCollectionClick(collectionId: String?)
    }
}
