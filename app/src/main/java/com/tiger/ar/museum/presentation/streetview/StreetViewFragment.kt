package com.tiger.ar.museum.presentation.streetview

import com.google.android.gms.maps.StreetViewPanorama
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.StreetViewPanoramaCamera
import com.google.android.gms.maps.model.StreetViewPanoramaLocation
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation
import com.google.firebase.firestore.GeoPoint
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.databinding.StreetViewFragmentBinding

class StreetViewFragment: MuseumFragment<StreetViewFragmentBinding>(R.layout.street_view_fragment),
    StreetViewPanorama.OnStreetViewPanoramaChangeListener, StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener,
    StreetViewPanorama.OnStreetViewPanoramaClickListener, StreetViewPanorama.OnStreetViewPanoramaLongClickListener {
    companion object {
        const val GEO_POINT_KEY = "GEO_POINT_KEY"
    }

    var location: GeoPoint? = null
    private val SYDNEY = LatLng(-33.87365, 151.20689)

    private lateinit var streetViewPanorama: StreetViewPanorama

    private var panoChangeTimes = 0
    private var panoCameraChangeTimes = 0
    private var panoClickTimes = 0
    private var panoLongClickTimes = 0

    override fun onPrepareInitView() {
        super.onPrepareInitView()
//        location = arguments?.getParcelable(GEO_POINT_KEY)
    }

    override fun onInitView() {
        super.onInitView()
        val streetViewPanoramaFragment = SupportStreetViewPanoramaFragment()
        fragmentManager?.beginTransaction()?.add(R.id.flStreetViewContainer, streetViewPanoramaFragment)?.commit()
        streetViewPanoramaFragment.getStreetViewPanoramaAsync { panorama ->
//            val location = LatLng(-23.594662377984672, -46.635563873011975)
//            panorama.apply {
//                setPosition(location)
//                isPanningGesturesEnabled = true
//                isUserNavigationEnabled = true
//                isStreetNamesEnabled = true
//                isZoomGesturesEnabled = true
//            }

            streetViewPanorama = panorama
            streetViewPanorama.setOnStreetViewPanoramaChangeListener(
                this@StreetViewFragment
            )
            streetViewPanorama.setOnStreetViewPanoramaCameraChangeListener(
                this@StreetViewFragment
            )
            streetViewPanorama.setOnStreetViewPanoramaClickListener(
                this@StreetViewFragment
            )
            streetViewPanorama.setOnStreetViewPanoramaLongClickListener(
                this@StreetViewFragment
            )
            streetViewPanorama.setPosition(getLatLng(location))
        }
    }

    override fun onStreetViewPanoramaChange(p0: StreetViewPanoramaLocation) {
//        panoChangeTimesTextView.text = "Times panorama changed=" + ++panoChangeTimes
    }

    override fun onStreetViewPanoramaCameraChange(p0: StreetViewPanoramaCamera) {
//        panoCameraChangeTextView.text = "Times camera changed=" + ++panoCameraChangeTimes
    }

    override fun onStreetViewPanoramaClick(orientation: StreetViewPanoramaOrientation) {
        val point = streetViewPanorama.orientationToPoint(orientation)
        point?.let {
            panoClickTimes++
//            panoClickTextView.text = "Times clicked=$panoClickTimes : $point"
            streetViewPanorama.animateTo(
                StreetViewPanoramaCamera.Builder()
                    .orientation(orientation)
                    .zoom(streetViewPanorama.panoramaCamera.zoom)
                    .build(), 1000
            )
        }
    }

    override fun onStreetViewPanoramaLongClick(orientation: StreetViewPanoramaOrientation) {
        val point = streetViewPanorama.orientationToPoint(orientation)
        if (point != null) {
            panoLongClickTimes++
//            panoLongClickTextView.text = "Times long clicked=$panoLongClickTimes : $point"
        }
    }

    private fun getLatLng(geoPoint: GeoPoint?): LatLng {
        return geoPoint?.let {
            LatLng(it.latitude, it.longitude)
        } ?: SYDNEY
    }
}
