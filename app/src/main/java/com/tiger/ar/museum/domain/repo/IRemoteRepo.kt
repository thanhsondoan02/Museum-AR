package com.tiger.ar.museum.domain.repo

import com.tiger.ar.museum.domain.model.MCollection
import com.tiger.ar.museum.domain.model.Model3d

interface IRemoteRepo {
    fun getListCollections(): List<MCollection>
    fun getModelInCollection(collectionId: Int): List<Model3d>
}
