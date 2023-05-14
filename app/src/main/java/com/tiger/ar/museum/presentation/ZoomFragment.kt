package com.tiger.ar.museum.presentation

import android.annotation.SuppressLint
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.databinding.ZoomFragmentBinding

class ZoomFragment : MuseumFragment<ZoomFragmentBinding>(R.layout.zoom_fragment) {
    companion object {
        const val IMAGE_URL_KEY = "IMAGE_URL_KEY"
        const val ORIGIN_SCALE_FACTOR = 1.5f
    }

    private var imageUrl: String? = null
    private var preIsScroll = false

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        imageUrl = arguments?.getString(IMAGE_URL_KEY)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onInitView() {
        super.onInitView()
        realMainActivity.apply {
            preIsScroll = enableScrollHideActionBar(false)
            expandAppBar()
            setBackIcon()

        }
        binding.ivZoom.apply {
            loadImage(imageUrl)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realMainActivity.enableScrollHideActionBar(preIsScroll)
    }
}
