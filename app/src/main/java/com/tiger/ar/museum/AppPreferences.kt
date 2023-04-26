package com.tiger.ar.museum

import com.tiger.ar.museum.domain.model.User

object AppPreferences {
    private var user: User? = null

    fun getUserInfo(): User {
        return user!!
    }

    fun setUserInfo(user: User) {
        this.user = user
    }
}
