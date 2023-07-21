package com.tiger.ar.museum

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.tiger.ar.museum.common.extension.getApplication
import com.tiger.ar.museum.domain.model.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object AppPreferences {
    private const val SHARE_PREF_NAME = "TIGER"
    private const val EMAIL_KEY = "EMAIL_KEY"
    private const val PASSWORD_KEY = "PASSWORD_KEY"

    private var user: User? = null

    // Khởi tạo instance của SharedPreferences
    private lateinit var prefsShared: SharedPreferences

    /**
     * Hàm này dùng để khởi tạo instance của SharedPreference dùng chung cho toàn ứng dụng
     * @created_by ppdang on 5/8/2019
     **/
    fun init(application: Application) {
        prefsShared = application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
    }

    fun getUserInfo(): User {
        return user!!
    }

    fun getUserId(): String {
        return try {
            user?.key!!
        } catch (e: Exception) {
            Toast.makeText(getApplication(), "User key is null", Toast.LENGTH_SHORT).show()
            throw Exception("User key is null")
        }
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

    private fun edit(operator: (SharedPreferences.Editor) -> Unit) {
        try {
            val prefsEditor = prefsShared.edit()
            operator(prefsEditor)
            prefsEditor.apply()
        } catch (e: Exception) {
//            Logger.e(TAG, e)
        }
    }

    fun getString(key: String, defaultValue: String? = null): String? =
        prefsShared.getString(key, defaultValue)

    fun setString(key: String, value: String?) = edit {
        it.putString(key, value)
    }

    inline fun <reified T> getValueJson(key: String, defaultValue: String? = "") =
        try {
            getString(key, defaultValue)?.let {
                Json.decodeFromString<T>(it)
            }
        } catch (e: Exception) {
            null
        }

    inline fun <reified T> setValueJson(key: String, value: T) {
        setString(key, Json.encodeToString(value))
    }
}
