package com.tiger.ar.museum.presentation.intro

import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.extension.getAppFont
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.SignUpActivityBinding
import com.tiger.ar.museum.domain.model.User
import com.tiger.ar.museum.presentation.RealMainActivity
import com.tiger.ar.museum.presentation.dialog.LoadingDialog


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
        binding.etvSignUpPasswordAgain.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signUp()
                true
            } else {
                false
            }
        }
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
        val email = binding.etvSignUpEmail.text.toString()
        val password = binding.etvSignUpPassword.text.toString()
        val passwordAgain = binding.etvSignUpPasswordAgain.text.toString()

        // check if email is empty
        if (email.isEmpty()) {
            toast(getAppString(R.string.email_empty))
            return
        }

        // check if password is empty
        if (password.isEmpty() || passwordAgain.isEmpty()) {
            toast(getAppString(R.string.password_empty))
            return
        }

        // check if password is match
        if (password != passwordAgain) {
            toast(getAppString(R.string.password_not_match))
            return
        }

        // show loading dialog
        val loadingDialog = LoadingDialog()
        loadingDialog.show(supportFragmentManager, loadingDialog::class.java.simpleName)

        // check if email is already registered
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        val query = ref.orderByChild("email").equalTo(email).limitToFirst(1)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.childrenCount != 0L) {
                    loadingDialog.dismiss()
                    toast(getAppString(R.string.account_exist))
                    return
                } else {
                    addUserToDatabase(User(email = email, password = password), loadingDialog)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                loadingDialog.dismiss()
                toast(getAppString(R.string.sign_up_fail) + ": ${error.message}")
                return
            }
        })
    }

    private fun addUserToDatabase(user: User, dialog: LoadingDialog) {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.push().setValue(user)
            .addOnCompleteListener {
                dialog.dismiss()
                toast(getAppString(R.string.sign_up_success))
                setAppPreference(user)
                navigateToAndClearStack(RealMainActivity::class.java)
            }.addOnFailureListener {
                dialog.dismiss()
                toast(getAppString(R.string.sign_up_fail) + ": ${it.message}")
            }
    }

    private fun setAppPreference(user: User) {
        AppPreferences.setUserInfo(user)
        AppPreferences.saveLoginInfo()
    }

}
