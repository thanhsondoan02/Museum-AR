package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    var name: String? = null,

    var email: String? = null,

    var password: String? = null,

    var avatar: String? = null

) : MuseumModel()
