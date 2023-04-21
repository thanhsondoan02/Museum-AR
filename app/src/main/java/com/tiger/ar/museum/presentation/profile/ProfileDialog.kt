package com.tiger.ar.museum.presentation.profile

import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumDialog
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
    }

    private fun initOnClick() {
        binding.ivProfileClose.setOnSafeClick { dismiss() }
        binding.llProfileContent.setOnSafeClick { }
    }
}
