package com.tiger.ar.museum.presentation.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.R
import com.tiger.ar.museum.domain.model.User
import com.tiger.ar.museum.presentation.RealMainActivity

class CheckPrefActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.check_pref_activity)
        super.onCreate(savedInstanceState)
        if (AppPreferences.getLoginInfo()) {
            login()
        } else {
            startActivity(Intent(this, IntroductionActivity::class.java))
            finish()
        }
    }

    private fun login() {
        val email = AppPreferences.getUserInfo().email
        val password = AppPreferences.getUserInfo().password
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        val query = ref.orderByChild("email").equalTo(email).limitToFirst(1)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.childrenCount == 0L) {
                    return
                }
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null && user.email == email && user.password == password) {
                        user.key = userSnapshot.key
                        setAppPreference(user)
                        startActivity(Intent(this@CheckPrefActivity, RealMainActivity::class.java))
                        finish()
                    } else {
                        AppPreferences.clearLoginInfo()
                        startActivity(Intent(this@CheckPrefActivity, IntroductionActivity::class.java))
                        finish()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun setAppPreference(user: User) {
        AppPreferences.setUserInfo(user)
        AppPreferences.saveLoginInfo()
    }
}
