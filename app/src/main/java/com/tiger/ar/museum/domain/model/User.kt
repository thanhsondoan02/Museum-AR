package com.tiger.ar.museum.domain.model

import com.tiger.ar.museum.IParcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    var email: String? = null,

    var password: String? = null,

    var avatar: String? = null

) : IParcelable
