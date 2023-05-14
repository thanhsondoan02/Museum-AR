package com.tiger.ar.museum.presentation.dialog

import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumDialog
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.gone
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.extension.show
import com.tiger.ar.museum.common.view.DialogScreen
import com.tiger.ar.museum.databinding.LoadingDialogBinding

class LoadingDialog : MuseumDialog<LoadingDialogBinding>(R.layout.loading_dialog) {

    var title: String? = null
    var isEnableDismiss = false

    override fun getBackgroundId() = R.id.flLoadingRoot

    override fun screen() = DialogScreen().apply {
        isFullWidth = true
        isFullHeight = true
        isDismissByTouchOutSide = false
        isDismissByOnBackPressed = isEnableDismiss
    }

    override fun onInitView() {
        binding.tvLoadingDialogMessage.text = title ?: getAppString(R.string.please_wait)

        if (isEnableDismiss) {
            binding.mcvLoadingClose.show()
            binding.mcvLoadingClose.setOnSafeClick {
                dismiss()
            }
        } else {
            binding.mcvLoadingClose.gone()
        }
    }
}
