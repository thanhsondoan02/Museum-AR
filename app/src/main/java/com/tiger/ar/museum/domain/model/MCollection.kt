package com.tiger.ar.museum.domain.model

import com.tiger.ar.museum.IParcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MCollection(

    var id: Int? = null,

    var name: String? = null,

    var thumbnail: String? = null

) : IParcelable
