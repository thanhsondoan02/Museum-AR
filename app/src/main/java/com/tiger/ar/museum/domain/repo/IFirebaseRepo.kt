package com.tiger.ar.museum.domain.repo

import java.io.File

interface IFirebaseRepo {
    fun get3DModel(name: String): File
}
