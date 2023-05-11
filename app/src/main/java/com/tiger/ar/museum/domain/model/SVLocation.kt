package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class SVLocation(

    var latitude: Double? = null,

    var longitude: Double? = null

) : MuseumModel()
