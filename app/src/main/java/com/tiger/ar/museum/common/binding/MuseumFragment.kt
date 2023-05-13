package com.tiger.ar.museum.common.binding

import androidx.databinding.ViewDataBinding
import com.tiger.ar.museum.presentation.RealMainActivity

abstract class MuseumFragment<DB : ViewDataBinding>(layoutId: Int) : BaseBindingFragment<DB>(layoutId) {
    protected val museumActivity by lazy {
        requireActivity() as MuseumActivity<*>
    }

    protected val realMainActivity by lazy {
        requireActivity() as RealMainActivity
    }
}

