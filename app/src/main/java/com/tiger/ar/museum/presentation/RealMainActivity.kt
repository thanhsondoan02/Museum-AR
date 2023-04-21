package com.tiger.ar.museum.presentation

import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.FontSpan
import com.tiger.ar.museum.common.SpannableBuilder
import com.tiger.ar.museum.common.binding.BaseBindingActivity
import com.tiger.ar.museum.common.extension.getAppFont
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.databinding.RealMainActivityBinding
import com.tiger.ar.museum.presentation.profile.ProfileDialog

class RealMainActivity : BaseBindingActivity<RealMainActivityBinding>(R.layout.real_main_activity) {

//    override fun getContainerId() = R.id.flRealMainContainer

    override fun onInitView() {
        super.onInitView()
        initOnClick()
        setSpanTitle()
    }

    private fun initOnClick() {
        binding.ivRealMainCast.setOnSafeClick {

        }

        binding.ivRealMainSearch.setOnSafeClick {
        }

        binding.ivRealMainProfile.setOnSafeClick {
            val profileDialog = ProfileDialog()
            profileDialog.show(supportFragmentManager, profileDialog::class.java.simpleName)
        }
    }

    private fun setSpanTitle() {
        binding.tvRealMainTitle.text = SpannableBuilder(getAppString(R.string.tiger) + " ")
            .withSpan(FontSpan(getAppFont(R.font.roboto_bold)))
            .appendText(getAppString(R.string.app_name))
            .withSpan(FontSpan(getAppFont(R.font.roboto_regular)))
            .spannedText
    }
}
