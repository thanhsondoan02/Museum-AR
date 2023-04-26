package com.tiger.ar.museum.presentation.login

import android.view.inputmethod.EditorInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.LoginFragment2Binding
import com.tiger.ar.museum.domain.model.User
import com.tiger.ar.museum.presentation.RealMainActivity
import com.tiger.ar.museum.presentation.dialog.LoadingDialog

class LoginActivity : MuseumActivity<LoginFragment2Binding>(R.layout.login_fragment_2) {

    override fun onInitView() {
        super.onInitView()
        initOnClick()
    }

    private fun initOnClick() {
        binding.tvLoginForgotPassword.setOnSafeClick { }
        binding.llLoginFacebook.setOnSafeClick { }
        binding.llLoginGoogle.setOnSafeClick { }
        binding.tvLoginSignUp.setOnSafeClick { }
        binding.btnLogin.setOnSafeClick { login() }
        binding.etvLoginPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login()
                true
            } else {
                false
            }
        }
    }

    private fun login() {
        val dialog = LoadingDialog()
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
                            saveSharedPreference(user)
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

    private fun saveSharedPreference(user: User) {

    }

    private fun setAppPreference(user: User) {
        AppPreferences.user = user
    }
}
