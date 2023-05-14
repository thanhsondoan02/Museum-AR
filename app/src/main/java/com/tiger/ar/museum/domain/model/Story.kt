package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(

    var title: String? = null,

    var description: String? = null,

    var thumbnail: String? = null,

    var collectionId: String? = null

) : MuseumModel()
