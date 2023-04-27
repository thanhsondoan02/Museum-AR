package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(

    var id: Int? = null,

    var authorId: Int? = null,

    var name: String? = null,

    var thumbnail: String? = null,

    var time: String? = null,

    var streetView: String? = null,

    var model3d: String? = null

) : MuseumModel()
