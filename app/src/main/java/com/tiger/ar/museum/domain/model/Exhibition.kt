package com.tiger.ar.museum.domain.model

import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exhibition(

    var name: String? = null,

    var museumName: String? = null,

    var place: String? = null,

    var thumbnail: String? = null,

    var museumThumb: String? = null,

    var startTime: Timestamp? = null,

    var endTime: Timestamp? = null

) : MuseumModel()
