package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(

    var title: String? = null,

    var description: String? = null,

    var thumbnail: String? = null,

    var collectionId: String? = null,

    var pages: List<Page>? = null

) : MuseumModel()

@Parcelize
data class Page(

    var description: String? = null,

    var thumbnail: String? = null

) : MuseumModel()
