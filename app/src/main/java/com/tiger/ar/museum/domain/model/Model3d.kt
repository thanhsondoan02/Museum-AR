package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class Model3d(

    var id: Int? = null,

    var name: String? = null,

    var thumbnail: String? = null,

    var description: String? = null,

    var path: String? = null

) : MuseumModel()
