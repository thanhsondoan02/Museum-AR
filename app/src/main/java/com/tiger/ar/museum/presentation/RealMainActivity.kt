package com.tiger.ar.museum.presentation

import android.os.Bundle
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.BaseBindingActivity
import com.tiger.ar.museum.common.extension.onSafeClick
import com.tiger.ar.museum.databinding.RealMainActivityBinding

class RealMainActivity : BaseBindingActivity<RealMainActivityBinding>(R.layout.real_main_activity) {

    override fun getContainerId() = R.id.flMainActivityContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tvMainActivityHelloWorld.onSafeClick {
            replaceFragment(MyArFragment())
        }
    }

    override fun onInitView() {
        super.onInitView()

    }
}
