package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteData(

    var collections: List<MCollection>? = null,

    var items: List<Item>? = null,

    var story: List<Story>? = null,

) : MuseumModel()
