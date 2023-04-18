package com.tiger.ar.museum.data.repo

import com.tiger.ar.museum.data.BaseRepo
import com.tiger.ar.museum.data.IMockService
import com.tiger.ar.museum.data.convert.CollectionMainDTOConvertToCollectionMain
import com.tiger.ar.museum.data.convert.ModelMainDTOConvertToModelMain
import com.tiger.ar.museum.data.invokeApi
import com.tiger.ar.museum.data.invokeMockService
import com.tiger.ar.museum.domain.model.MCollection
import com.tiger.ar.museum.domain.model.Model3d
import com.tiger.ar.museum.domain.repo.IRemoteRepo

class RemoteRepoImpl : IRemoteRepo, BaseRepo() {
    override fun getListCollections(): List<MCollection> {
        val service = invokeMockService(IMockService::class.java)
        return service.getCollections().invokeApi { _, body ->
            CollectionMainDTOConvertToCollectionMain().convert(body.data!!).collectionsList ?: emptyList()
        }
    }

    override fun getModelInCollection(collectionId: Int): List<Model3d> {
        val service = invokeMockService(IMockService::class.java)
        return service.getModelsInCollection(collectionId).invokeApi { _, body ->
            ModelMainDTOConvertToModelMain().convert(body.data!!).modelsList ?: emptyList()
        }
    }
}
