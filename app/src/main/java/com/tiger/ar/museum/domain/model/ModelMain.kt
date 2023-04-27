package com.tiger.ar.museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelMain(

    var description: String? = null,

    var modelsList: List<Model3d>? = null

) : MuseumModel()
