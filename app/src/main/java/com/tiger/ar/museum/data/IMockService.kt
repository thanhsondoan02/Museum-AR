package com.tiger.ar.museum.data

import com.tiger.ar.museum.data.model.CollectionResponse
import com.tiger.ar.museum.data.model.ModelInCollectionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IMockService : IApiService {
    @GET("collections")
    fun getCollections(): Call<CollectionResponse>

    @GET("collections")
    fun getModelsInCollection(@Query("id") collectionId: Int): Call<ModelInCollectionResponse>

}
