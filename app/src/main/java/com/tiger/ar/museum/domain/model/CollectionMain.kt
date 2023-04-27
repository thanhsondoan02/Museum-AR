package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class CollectionMain(

    var collectionsList: List<MCollection>? = null

) : MuseumModel()
