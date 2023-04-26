package com.tiger.ar.museum.presentation.intro

import android.view.inputmethod.EditorInfo
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.databinding.SignUpActivityBinding


class SignUpActivity: MuseumActivity<SignUpActivityBinding>(R.layout.sign_up_activity) {

    override fun onInitView() {
        super.onInitView()
        initOnClick()
    }

    private fun initOnClick() {
        binding.ivSignUpShowPassword.setOnSafeClick {
            if (binding.etvSignUpPassword.inputType == EditorInfo.TYPE_TEXT_VARIATION_PASSWORD) {
                binding.etvSignUpPassword.inputType = EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.ivSignUpShowPassword.setImageResource(R.drawable.ic_hide_password_gray)
            } else {
                binding.etvSignUpPassword.inputType = EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
                binding.ivSignUpShowPassword.setImageResource(R.drawable.ic_show_password_gray)
            }
        }
    }

}
