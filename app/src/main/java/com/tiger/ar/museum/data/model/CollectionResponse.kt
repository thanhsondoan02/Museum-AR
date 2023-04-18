package com.tiger.ar.museum.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tiger.ar.museum.data.BaseApiResponse

class CollectionResponse : BaseApiResponse() {
    @SerializedName("data")
    @Expose
    var data: CollectionMainDTO? = null
}
