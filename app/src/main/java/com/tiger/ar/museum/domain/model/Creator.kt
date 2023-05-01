package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class Creator(

    var name: String? = null

) : MuseumModel()
