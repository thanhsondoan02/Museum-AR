package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteData(

    var collections: MutableList<SimpleModel?>? = null,

    var items: MutableList<SimpleModel?>? = null,

    var stories: MutableList<SimpleModel?>? = null,

) : MuseumModel()
