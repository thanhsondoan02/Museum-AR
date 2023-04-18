package com.tiger.ar.museum.domain.model

import com.tiger.ar.museum.IParcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CollectionMain(

    var collectionsList: List<MCollection>? = null

) : IParcelable
