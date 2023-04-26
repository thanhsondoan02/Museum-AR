package com.tiger.ar.museum.presentation.profile

import android.text.style.ForegroundColorSpan
import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.SpannableBuilder
import com.tiger.ar.museum.common.binding.MuseumDialog
import com.tiger.ar.museum.common.extension.getAppColor
import com.tiger.ar.museum.common.extension.getAppDrawable
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.view.DialogScreen
import com.tiger.ar.museum.databinding.ProfileDialogBinding

class ProfileDialog : MuseumDialog<ProfileDialogBinding>(R.layout.profile_dialog) {

    override fun getBackgroundId() = R.id.flProfileBackground

    override fun screen() = DialogScreen().apply {
        isFullWidth = true
        isFullHeight = true
        isDismissByTouchOutSide = true
        isDismissByOnBackPressed = true
    }

    override fun onInitView() {
        initOnClick()
        setTitleColor()
        AppPreferences.getUserInfo().let {
            binding.ivProfileAvatar.loadImage(it.avatar, placeHolder = getAppDrawable(R.drawable.ic_no_picture))
            binding.tvProfileEmail.text = it.email
            binding.tvProfileFullName.text = it.name
        }
    }

    private fun initOnClick() {
        binding.ivProfileClose.setOnSafeClick { dismiss() }
        binding.llProfileContent.setOnSafeClick { }
        binding.flProfileDown.setOnSafeClick { }
    }

    private fun setTitleColor() {
        binding.tvProfileTiger.text = SpannableBuilder("T")
            .withSpan(ForegroundColorSpan(getAppColor(R.color.blue)))
            .appendText("i")
            .withSpan(ForegroundColorSpan(getAppColor(R.color.yellow)))
            .appendText("g")
            .withSpan(ForegroundColorSpan(getAppColor(R.color.green)))
            .appendText("e")
            .withSpan(ForegroundColorSpan(getAppColor(R.color.red)))
            .appendText("r")
            .withSpan(ForegroundColorSpan(getAppColor(R.color.purple)))
            .spannedText
    }
}
