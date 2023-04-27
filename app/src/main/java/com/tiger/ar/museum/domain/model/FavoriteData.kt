package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteData(

    var collections: List<SimpleModel>? = null,

    var items: List<SimpleModel>? = null,

    var stories: List<SimpleModel>? = null,

) : MuseumModel()
