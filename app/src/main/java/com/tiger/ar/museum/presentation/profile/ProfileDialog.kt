package com.tiger.ar.museum.presentation.profile

import android.text.style.ForegroundColorSpan
import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.SpannableBuilder
import com.tiger.ar.museum.common.binding.MuseumDialog
import com.tiger.ar.museum.common.extension.*
import com.tiger.ar.museum.common.view.DialogScreen
import com.tiger.ar.museum.databinding.ProfileDialogBinding

class ProfileDialog : MuseumDialog<ProfileDialogBinding>(R.layout.profile_dialog) {
    var listener: IListener? = null

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
        binding.llProfileContent.setOnSafeClick { /* do no thing */ }
        binding.flProfileDown.setOnSafeClick { toastUndeveloped() }
        binding.mcvProfileManageAccount.setOnSafeClick { toastUndeveloped() }
        binding.llProfileAchievement.setOnSafeClick { toastUndeveloped() }
        binding.llProfileCollection.setOnSafeClick { toastUndeveloped() }
        binding.llProfileNearBy.setOnSafeClick { toastUndeveloped() }
        binding.llProfileExperiments.setOnSafeClick { toastUndeveloped() }
        binding.llProfileDataInApp.setOnSafeClick { toastUndeveloped() }
        binding.llProfileSettings.setOnSafeClick { listener?.onSetting() }
        binding.llProfileFeedback.setOnSafeClick { toastUndeveloped() }
        binding.tvProfilePrivacyPolicy.setOnSafeClick { toastUndeveloped() }
        binding.tvProfileTermsOfService.setOnSafeClick { toastUndeveloped() }
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

    interface IListener {
        fun onSetting()
    }
}
