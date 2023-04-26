package com.tiger.ar.museum.presentation.intro

import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.extension.getAppFont
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.databinding.SignUpActivityBinding


class SignUpActivity : MuseumActivity<SignUpActivityBinding>(R.layout.sign_up_activity) {

    override fun onInitView() {
        super.onInitView()
        initOnClick()
    }

    private fun initOnClick() {
        setPasswordEditText(binding.etvSignUpPassword, binding.ivSignUpShowPassword)
        setPasswordEditText(binding.etvSignUpPasswordAgain, binding.ivSignUpShowPasswordAgain)
        binding.btnSignUp.setOnSafeClick { signUp() }
        binding.tvSignUpSignIn.setOnSafeClick { navigateTo(LoginActivity::class.java) }
    }

    private fun setPasswordEditText(editText: EditText, imageView: ImageView) {
        imageView.setOnSafeClick {
            if (editText.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                hidePassword(editText, imageView)
            } else {
                showPassword(editText, imageView)
            }
        }
    }

    private fun hidePassword(editText: EditText, imageView: ImageView) {
        editText.apply {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            setSelection(length())
            typeface = getAppFont(R.font.roboto_regular)
        }
        imageView.setImageResource(R.drawable.ic_show_password_gray)
    }

    private fun showPassword(editText: EditText, imageView: ImageView) {
        editText.apply {
            inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            setSelection(length())
        }
        imageView.setImageResource(R.drawable.ic_hide_password_gray)
    }

    private fun signUp() {

    }

}
