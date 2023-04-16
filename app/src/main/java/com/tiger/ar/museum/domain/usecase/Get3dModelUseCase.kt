package com.tiger.ar.museum.domain.usecase

import com.tiger.ar.museum.common.BaseUseCase
import com.tiger.ar.museum.di.RepositoryFactory
import java.io.File

class Get3dModelUseCase : BaseUseCase<Get3dModelUseCase.Get3dModelUseCaseRV, File>() {
    override suspend fun execute(rv: Get3dModelUseCaseRV): File {
        val repo = RepositoryFactory.getFirebaseRepo()
        if (rv.name == null) {
            throw IllegalArgumentException("model name is null")
        }
        return repo.get3DModel(rv.name)
    }

    class Get3dModelUseCaseRV(val name: String?) : RequestValue
}
