package com.tiger.ar.museum.domain.usecase

import com.tiger.ar.museum.common.BaseUseCase
import com.tiger.ar.museum.di.RepositoryFactory
import com.tiger.ar.museum.domain.model.Model3d

class GetAllModelUseCase : BaseUseCase<BaseUseCase.VoidRequest, List<Model3d>>() {
    override suspend fun execute(rv: VoidRequest): List<Model3d> {
        val repo = RepositoryFactory.getRemoteRepo()
        return repo.getModelInCollection(1)
    }
}
