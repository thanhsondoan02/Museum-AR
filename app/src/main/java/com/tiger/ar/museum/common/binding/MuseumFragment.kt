package com.tiger.ar.museum.common.binding

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.common.navigation.FadeAnim
import com.tiger.ar.museum.common.navigation.IScreenAnim
import com.tiger.ar.museum.common.view.BaseFragment
import com.tiger.ar.museum.presentation.RealMainActivity

abstract class MuseumFragment<DB : ViewDataBinding>(layoutId: Int) : BaseBindingFragment<DB>(layoutId) {
    protected val museumActivity by lazy {
        requireActivity() as MuseumActivity<*>
    }

    protected val realMainActivity by lazy {
        requireActivity() as RealMainActivity
    }

    protected fun replaceFragmentNew(
        fragment: BaseFragment,
        bundle: Bundle? = null,
        keepToBackStack: Boolean = true,
        screenAnim: IScreenAnim = FadeAnim(),
        containerId: Int
    ) {
        realMainActivity.replaceFragmentNew(fragment, bundle, keepToBackStack, screenAnim, containerId)
    }

    protected fun addFragmentNew(
        fragment: BaseFragment,
        bundle: Bundle? = null,
        keepToBackStack: Boolean = true,
        screenAnim: IScreenAnim = FadeAnim(),
        containerId: Int
    ) {
        realMainActivity.addFragmentNew(fragment, bundle, keepToBackStack, screenAnim, containerId)
    }
}

