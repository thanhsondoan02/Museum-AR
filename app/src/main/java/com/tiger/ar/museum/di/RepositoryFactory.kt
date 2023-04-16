package com.tiger.ar.museum.di

import com.tiger.ar.museum.data.repo.FirebaseRepoImpl
import com.tiger.ar.museum.domain.repo.IFirebaseRepo

object RepositoryFactory {
    private val firebaseRepoImpl = FirebaseRepoImpl()

    fun getFirebaseRepo(): IFirebaseRepo {
        return firebaseRepoImpl
    }
}
