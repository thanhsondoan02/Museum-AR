package com.tiger.ar.museum.di

import com.tiger.ar.museum.data.repo.FirebaseRepoImpl
import com.tiger.ar.museum.data.repo.RemoteRepoImpl
import com.tiger.ar.museum.domain.repo.IFirebaseRepo
import com.tiger.ar.museum.domain.repo.IRemoteRepo

object RepositoryFactory {
    private val firebaseRepoImpl = FirebaseRepoImpl()
    private val remoteRepoImpl = RemoteRepoImpl()

    fun getFirebaseRepo(): IFirebaseRepo {
        return firebaseRepoImpl
    }

    fun getRemoteRepo(): IRemoteRepo {
        return remoteRepoImpl
    }
}
