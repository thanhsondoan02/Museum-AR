package com.tiger.ar.museum.presentation.intro

import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.extension.getAppFont
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.LoginFragment2Binding
import com.tiger.ar.museum.domain.model.User
import com.tiger.ar.museum.presentation.RealMainActivity
import com.tiger.ar.museum.presentation.dialog.LoadingDialog2

class LoginActivity : MuseumActivity<LoginFragment2Binding>(R.layout.login_fragment_2) {

    override fun onInitView() {
        super.onInitView()
        initOnClick()
    }

    private fun initOnClick() {
        binding.tvLoginForgotPassword.setOnSafeClick { }
        binding.llLoginFacebook.setOnSafeClick { }
        binding.llLoginGoogle.setOnSafeClick { }
        binding.tvLoginSignUp.setOnSafeClick {
            navigateTo(SignUpActivity::class.java)
        }
        binding.btnLogin.setOnSafeClick { newLogin() }
        binding.etvLoginPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                newLogin()
                true
            } else {
                false
            }
        }
        setPasswordEditText(binding.etvLoginPassword, binding.ivLoginShowPassword)
    }

    private fun newLogin() {
        val dialog = LoadingDialog2()
        dialog.show(supportFragmentManager, dialog::class.java.simpleName)

        val email = binding.etvLoginEmail.text.toString()
        val password = binding.etvLoginPassword.text.toString()

        val userRef = Firebase.firestore.collection("users")
        userRef.whereEqualTo("email", email).get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty || querySnapshot.documents.isEmpty()) {
                    dialog.dismiss()
                    toast(getAppString(R.string.account_does_not_exist))
                    return@addOnSuccessListener
                }
                for (document in querySnapshot.documents) {
                    val user = document.toObject(User::class.java)
                    if (user?.email == email) {
                        if (user.password == password) {
                            user.key = document.id
                            setAppPreference(user)
                            navigateToAndClearStack(RealMainActivity::class.java)
                        } else {
                            dialog.dismiss()
                            toast(getAppString(R.string.wrong_password))
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                toast("Error getting documents: $exception")
            }
    }

    private fun login() {
        val dialog = LoadingDialog2()
        dialog.show(supportFragmentManager, dialog::class.java.simpleName)

        val email = binding.etvLoginEmail.text.toString()
        val password = binding.etvLoginPassword.text.toString()
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        val query = ref.orderByChild("email").equalTo(email).limitToFirst(1)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.childrenCount == 0L) {
                    dialog.dismiss()
                    toast(getAppString(R.string.account_does_not_exist))
                    return
                }
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user?.email == email) {
                        if (user.password == password) {
                            user.key = userSnapshot.key
                            setAppPreference(user)
                            navigateToAndClearStack(RealMainActivity::class.java)
                        } else {
                            dialog.dismiss()
                            toast(getAppString(R.string.wrong_password))
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
                toast(getAppString(R.string.sign_in_fail) + ": ${error.message}")
            }
        })
    }

    private fun setAppPreference(user: User) {
        AppPreferences.setUserInfo(user)
        AppPreferences.saveLoginInfo()
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
}
