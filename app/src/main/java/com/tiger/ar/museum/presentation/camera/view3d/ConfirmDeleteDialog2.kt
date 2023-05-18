package com.tiger.ar.museum.presentation.camera.view3d

import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumDialog
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.view.DialogScreen
import com.tiger.ar.museum.databinding.ConfirmDeleteDialogBinding

class ConfirmDeleteDialog2 : MuseumDialog<ConfirmDeleteDialogBinding>(R.layout.confirm_delete_dialog_2) {
    var onConfirmAction: (() -> Unit)? = null

    override fun getBackgroundId() = R.id.flConfirmDeleteRoot

    override fun screen() = DialogScreen().apply {
        isFullWidth = true
        isFullHeight = true
        isDismissByTouchOutSide = false
        isDismissByOnBackPressed = true
    }

    override fun onInitView() {
        binding.mcvConfirmDeleteContent.setOnSafeClick {}
        binding.tvConfirmDeleteYes.setOnSafeClick { onConfirmAction?.invoke() }
        binding.tvConfirmDeleteNo.setOnSafeClick { dismiss() }
    }
}
