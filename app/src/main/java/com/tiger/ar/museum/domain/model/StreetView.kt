package com.tiger.ar.museum.domain.model

import com.google.firebase.firestore.GeoPoint
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
class StreetView(
    var name: String? = null,

    var place: String? = null,

    var location: @RawValue GeoPoint? = null,

    var thumbnail: String? = null
) : MuseumModel()
