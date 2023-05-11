package com.tiger.ar.museum.domain.model

import com.google.firebase.firestore.GeoPoint

class StreetView {
    var name: String? = null

    var place: String? = null

    var location: GeoPoint? = null

    var thumbnail: String? = null
}
