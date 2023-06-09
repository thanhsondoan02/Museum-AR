package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
class HomeData(

    var streetViews: List<StreetView>? = null,

    var collections: List<MCollection>? = null

) : MuseumModel()
