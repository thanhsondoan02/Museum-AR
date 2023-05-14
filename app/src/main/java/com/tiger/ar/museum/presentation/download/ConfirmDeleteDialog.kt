package com.tiger.ar.museum.presentation.download

import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumDialog
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.view.DialogScreen
import com.tiger.ar.museum.databinding.ConfirmDeleteDialogBinding

class ConfirmDeleteDialog : MuseumDialog<ConfirmDeleteDialogBinding>(R.layout.confirm_delete_dialog) {
    var name: String? = null
    var size: String? = null
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

        binding.tvConfirmDeleteSize.text = size
        binding.tvConfirmDeleteDesc.text = getTextDesc()
    }


    fun getTextDesc(): String {
        return getAppString(R.string.confirm_delete_desc1) + " $name " + getAppString(R.string.confirm_delete_desc2)
    }
}
