package com.tiger.ar.museum.domain.model

import com.tiger.ar.museum.IParcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gallery(

    var id: Int? = null,

    var title: String? = null,

    var description: String? = null,

    var items: List<Item>? = null

) : IParcelable
