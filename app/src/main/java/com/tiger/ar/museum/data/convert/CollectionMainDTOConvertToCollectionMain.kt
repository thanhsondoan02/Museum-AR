package com.tiger.ar.museum.data.convert

import com.tiger.ar.museum.common.Mapper
import com.tiger.ar.museum.data.model.CollectionDTO
import com.tiger.ar.museum.data.model.CollectionMainDTO
import com.tiger.ar.museum.domain.model.CollectionMain
import com.tiger.ar.museum.domain.model.MCollection

class CollectionMainDTOConvertToCollectionMain : IConverter<CollectionMainDTO, CollectionMain> {

    private val mapper = Mapper(CollectionMainDTO::class, CollectionMain::class).apply {
        addNameMapper(CollectionMain::collectionsList) {
            return@addNameMapper CollectionMainDTO::collectionsList
        }
    }

    private val collectionDTOConvertToMCollectionMapper = Mapper(CollectionDTO::class, MCollection::class).apply {
        addNameMapper(MCollection::id) {
            return@addNameMapper CollectionDTO::id
        }
        addNameMapper(MCollection::name) {
            return@addNameMapper CollectionDTO::name
        }
        addNameMapper(MCollection::thumbnail) {
            return@addNameMapper CollectionDTO::thumbnail
        }
    }

    override fun convert(source: CollectionMainDTO): CollectionMain {
        mapper.register(
            CollectionMainDTO::collectionsList,
            Mapper.listMapper(collectionDTOConvertToMCollectionMapper)
        )
        return mapper.invoke(source)
    }
}
