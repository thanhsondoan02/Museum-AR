package com.tiger.ar.museum.presentation.streetview

import androidx.fragment.app.FragmentManager
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
import com.tiger.ar.museum.presentation.RealMainActivity

class StreetViewFragment : MuseumFragment<StreetViewFragmentBinding>(R.layout.street_view_fragment),
    StreetViewPanorama.OnStreetViewPanoramaChangeListener, StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener,
    StreetViewPanorama.OnStreetViewPanoramaClickListener, StreetViewPanorama.OnStreetViewPanoramaLongClickListener {

    var location: GeoPoint? = null
    private val SYDNEY = LatLng(6.453811374553505, 3.3891655436795896)

    private lateinit var streetViewPanorama: StreetViewPanorama
    private var streetViewPanoramaFragment: SupportStreetViewPanoramaFragment? = null

    private var panoChangeTimes = 0
    private var panoCameraChangeTimes = 0
    private var panoClickTimes = 0
    private var panoLongClickTimes = 0

    override fun onInitView() {
        super.onInitView()
        initActionBar()
        initStreetView()
    }

    override fun onDestroy() {
        super.onDestroy()
        (museumActivity as RealMainActivity).supportFragmentManager.apply {
            if (fragments.lastOrNull() is SupportStreetViewPanoramaFragment) {
                if (streetViewPanoramaFragment != null) {
                    beginTransaction().remove(streetViewPanoramaFragment!!).commit();
                }
                popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
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
        return if (geoPoint == null || geoPoint.latitude == 0.0 || geoPoint.longitude == 0.0) {
            SYDNEY
        } else {
            LatLng(geoPoint.latitude, geoPoint.longitude)
        }
    }

    private fun initActionBar() {
        (museumActivity as RealMainActivity).apply {
            setBackIcon()
            enableScrollHideActionBar(false)
        }
    }

    private fun initStreetView() {
        streetViewPanoramaFragment = SupportStreetViewPanoramaFragment()
        fragmentManager?.beginTransaction()?.add(R.id.flStreetViewContainer, streetViewPanoramaFragment!!)?.commit()
        streetViewPanoramaFragment!!.getStreetViewPanoramaAsync { panorama ->
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
}
