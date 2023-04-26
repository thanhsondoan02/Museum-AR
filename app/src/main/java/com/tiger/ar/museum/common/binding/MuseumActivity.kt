package com.tiger.ar.museum.common.binding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.common.navigation.FadeAnim
import com.tiger.ar.museum.common.navigation.IScreenAnim
import com.tiger.ar.museum.common.view.BaseActivity
import com.tiger.ar.museum.common.view.BaseFragment

abstract class MuseumActivity<DB : ViewDataBinding>(layoutId: Int) : BaseBindingActivity<DB>(layoutId) {
    var mContainerId: Int = 0

    override fun getContainerId() = mContainerId

    fun replaceFragmentNew(
        fragment: BaseFragment,
        bundle: Bundle? = null,
        keepToBackStack: Boolean = true,
        screenAnim: IScreenAnim = FadeAnim(),
        containerId: Int
    ) {
        this.mContainerId = containerId
        super.replaceFragment(fragment, bundle, keepToBackStack, screenAnim)
    }

    fun setFullScreen() {
        window?.apply {
            WindowCompat.setDecorFitsSystemWindows(this, false)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v: View, windowInsets: WindowInsetsCompat ->
            if (!initSetFullScreen) {
                val systemBarInset = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.updatePadding(0, systemBarInset.top, 0, systemBarInset.bottom)
                initSetFullScreen = true
            }
            windowInsets
        }
    }

    fun navigateToAndClearStack(clazz: Class<out BaseActivity>, onCallback: (Intent) -> Unit = {}) {
        val intent = Intent(this, clazz)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        finishAffinity()
        onCallback.invoke(intent)
        startActivity(intent)
        animOpenScreen()
    }

}
