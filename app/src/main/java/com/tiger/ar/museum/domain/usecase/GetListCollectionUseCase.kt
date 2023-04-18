package com.tiger.ar.museum.domain.usecase

import com.tiger.ar.museum.common.BaseUseCase
import com.tiger.ar.museum.di.RepositoryFactory
import com.tiger.ar.museum.domain.model.MCollection

class GetListCollectionUseCase: BaseUseCase<BaseUseCase.VoidRequest, List<MCollection>>() {
    override suspend fun execute(rv: VoidRequest): List<MCollection> {
        val repo = RepositoryFactory.getRemoteRepo()
        return repo.getListCollections()
//        return mockListCollection()
    }
}
