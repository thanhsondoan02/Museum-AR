package com.tiger.ar.museum.presentation

import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.BaseBindingActivity
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.databinding.RealMainActivityBinding
import com.tiger.ar.museum.presentation.collection.CollectionFragment

class RealMainActivity : BaseBindingActivity<RealMainActivityBinding>(R.layout.real_main_activity) {

    override fun getContainerId() = R.id.flRealMainContainer

    override fun onInitView() {
        super.onInitView()
        binding.btnRealMainExplore.setOnSafeClick {
            replaceFragment(CollectionFragment())
        }
    }
}
