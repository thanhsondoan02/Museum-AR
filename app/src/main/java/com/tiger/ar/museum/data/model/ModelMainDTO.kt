package com.tiger.ar.museum.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tiger.ar.museum.domain.model.Model3d

data class ModelMainDTO(
    @SerializedName("description")
    @Expose
    var description: List<CollectionDTO>? = null,

    @SerializedName("models")
    @Expose
    var modelsList: List<Model3d>? = null
)
