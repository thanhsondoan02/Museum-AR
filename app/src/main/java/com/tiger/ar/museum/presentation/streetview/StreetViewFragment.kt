package com.tiger.ar.museum.presentation.streetview

import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.LatLng
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.databinding.StreetViewFragmentBinding


class StreetViewFragment: MuseumFragment<StreetViewFragmentBinding>(R.layout.street_view_fragment) {
    companion object {
        const val STREET_VIEW_KEY = "STREET_VIEW_KEY"
    }

    var streetViewUrl: String? = null

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        streetViewUrl = arguments?.getString(STREET_VIEW_KEY)
    }

    override fun onInitView() {
        super.onInitView()
        val streetViewPanoramaFragment = fragmentManager?.findFragmentById(R.id.fStreetView) as SupportStreetViewPanoramaFragment
        streetViewPanoramaFragment.getStreetViewPanoramaAsync { panorama ->
            val location = LatLng(-23.594662377984672, -46.635563873011975)
            panorama.apply {
                setPosition(location)
                isPanningGesturesEnabled = true
                isUserNavigationEnabled = true
                isStreetNamesEnabled = true
                isZoomGesturesEnabled = true
            }
        }
    }

}
