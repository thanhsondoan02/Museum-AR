package com.tiger.ar.museum.presentation.dialog

import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumDialog
import com.tiger.ar.museum.common.view.DialogScreen
import com.tiger.ar.museum.databinding.LoadingDialogBinding

class LoadingDialog2 : MuseumDialog<LoadingDialogBinding>(R.layout.loading_dialog_2) {

    override fun getBackgroundId() = R.id.flLoadingRoot

    override fun screen() = DialogScreen().apply {
        isFullWidth = true
        isFullHeight = true
        isDismissByTouchOutSide = false
        isDismissByOnBackPressed = false
    }

    override fun onInitView() {
    }
}
