package com.tiger.ar.museum.presentation

import android.annotation.SuppressLint
import android.view.ScaleGestureDetector
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.databinding.ZoomFragmentBinding
import java.lang.Float.max
import java.lang.Float.min

class ZoomFragment : MuseumFragment<ZoomFragmentBinding>(R.layout.zoom_fragment) {
    companion object {
        const val IMAGE_URL_KEY = "IMAGE_URL_KEY"
        const val ORIGIN_SCALE_FACTOR = 1.5f
    }

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = ORIGIN_SCALE_FACTOR
    private var imageUrl: String? = null

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        imageUrl = arguments?.getString(IMAGE_URL_KEY)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onInitView() {
        super.onInitView()
        binding.ivZoom.apply {
            loadImage(imageUrl)
//            scaleX = scaleFactor
//            scaleY = scaleFactor
        }
//        binding.flZoomContainer.setOnTouchListener { v, event ->
//            scaleGestureDetector.onTouchEvent(event)
//            true
//        }
//        scaleGestureDetector = ScaleGestureDetector(requireContext(), ScaleListener())
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = max(0.1f, min(scaleFactor, 10.0f))
            binding.ivZoom.scaleX = scaleFactor
            binding.ivZoom.scaleY = scaleFactor
            return true
        }
    }
}
