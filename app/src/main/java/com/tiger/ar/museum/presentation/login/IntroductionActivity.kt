package com.tiger.ar.museum.presentation.login

import android.graphics.Point
import android.graphics.drawable.Drawable
import android.view.WindowManager
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.extension.getAppDrawable
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.databinding.IntroductionActivityBinding

class IntroductionActivity : MuseumActivity<IntroductionActivityBinding>(R.layout.introduction_activity) {

    override fun onInitView() {
        super.onInitView()
        initOnClick()
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        getAppDrawable(R.drawable.intro_bg)?.let {
            binding.flIntroductionRoot.background = cropDrawable(it, 3/10f)
        }
    }

    private fun initOnClick() {
        binding.mcvIntroductionSignIn.setOnSafeClick {
            replaceFragmentNew(LoginFragment(), containerId = R.id.flIntroductionRoot)
        }

        binding.mcvIntroductionSignUp.setOnSafeClick {
            // TODO
        }
    }

    private fun cropDrawable(drawable: Drawable, ratio: Float): Drawable {
        val width = drawable.intrinsicWidth
        val height = (width / ratio).toInt()
        drawable.setBounds(0, 0, width, height)
        return drawable
    }

    private fun getScreenRatio(): Float {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x.toFloat() / size.y.toFloat()
    }
}
