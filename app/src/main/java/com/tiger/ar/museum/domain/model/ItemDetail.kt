package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemDetail(

    var title: String? = null,

    var description: String? = null

) : MuseumModel()
