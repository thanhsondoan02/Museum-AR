package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(

    var id: Int? = null,

    var title: String? = null,

    var description: String? = null,

    var thumbnail: String? = null

) : MuseumModel()
