package com.tiger.ar.museum.presentation.setting

import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.databinding.SettingFragmentBinding
import com.tiger.ar.museum.presentation.intro.IntroductionActivity

class SettingFragment : MuseumFragment<SettingFragmentBinding>(R.layout.setting_fragment) {

    override fun onInitView() {
        super.onInitView()
        initOnClick()
    }

    fun scrollToTop() {
        binding.nsvSettingFragment.smoothScrollTo(0, 0)
    }

    private fun initOnClick() {
        binding.constSettingOffline.setOnSafeClick {

        }
        binding.tvSettingLogOut.setOnSafeClick { logOut() }
    }

    private fun logOut() {
        AppPreferences.clearLoginInfo()
        museumActivity.navigateTo(IntroductionActivity::class.java)
        museumActivity.finish()
    }
}
