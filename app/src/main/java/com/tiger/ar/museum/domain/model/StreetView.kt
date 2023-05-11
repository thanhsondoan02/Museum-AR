package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class StreetView(

    var name: String? = null,

    var place: String? = null,

    var location: SVLocation? = null,

    var thumbnail: String? = null

) : MuseumModel()
