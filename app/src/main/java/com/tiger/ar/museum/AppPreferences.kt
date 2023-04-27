package com.tiger.ar.museum

import android.content.Context
import com.tiger.ar.museum.common.extension.getApplication
import com.tiger.ar.museum.domain.model.User

object AppPreferences {
    private const val SHARE_PREF_NAME = "TIGER"
    private const val EMAIL_KEY = "EMAIL_KEY"
    private const val PASSWORD_KEY = "PASSWORD_KEY"

    private var user: User? = null

    fun getUserInfo(): User {
        return user!!
    }

    fun setUserInfo(user: User) {
        this.user = user
    }

    fun saveLoginInfo() {
        val sharedPreferences = getApplication().getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE)
        if (user?.email == null || user?.password == null) return
        val editor = sharedPreferences.edit()
        editor.putString(EMAIL_KEY, user?.email)
        editor.putString(PASSWORD_KEY, user?.password)
        editor.apply()
    }

    fun getLoginInfo(): Boolean {
        val sharedPreferences = getApplication().getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(EMAIL_KEY, null)
        val password = sharedPreferences.getString(PASSWORD_KEY, null)
        return if (username != null && password != null) {
            user = User(email = username, password = password)
            true
        } else {
            false
        }
    }

    fun clearLoginInfo() {
        user = null
        val sharedPreferences = getApplication().getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
