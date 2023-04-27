package com.tiger.ar.museum.presentation.favorite

import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.domain.model.Gallery
import com.tiger.ar.museum.domain.model.Item
import com.tiger.ar.museum.domain.model.MCollection
import com.tiger.ar.museum.domain.model.Story

class FavoriteViewModel : BaseViewModel() {
    var listFavorite: MutableList<Any> = mockFavoriteTabData()
    var listGallery: MutableList<Any> = mockGalleriesData()

    private fun mockGalleriesData(): MutableList<Any> {
        val list = mutableListOf<Any>()
        list.add(FavoriteAdapter.HeaderDisplay().apply {
            avatarUrl = AppPreferences.getUserInfo().avatar
        })
        list.add(Gallery().apply {
            title = "Gallery 1"
            description = "Description 1"
            items = listOf(
                Item(thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg")
            )
        })
        list.add(Gallery().apply {
            title = "Gallery 2"
            description = "Description 2"
            items = listOf(
                Item(thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg"),
                Item(thumbnail = "https://i.pinimg.com/564x/a3/0d/94/a30d9464d035e1f30dd088d2ba89103d.jpg"),
                Item(thumbnail = "https://i.pinimg.com/564x/e4/78/fa/e478fae5d5ecf6f8537f248f6cab0d15.jpg")
            )
        })
        list.add(Gallery().apply {
            title = "Gallery 3"
            description = "Description 3"
            items = listOf(
                Item(thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg"),
                Item(thumbnail = "https://i.pinimg.com/564x/a3/0d/94/a30d9464d035e1f30dd088d2ba89103d.jpg")
            )
        })
        return list
    }

    private fun mockFavoriteTabData(): MutableList<Any> {
        val list = mutableListOf<Any>()
        list.add(FavoriteAdapter.HeaderDisplay().apply {
            avatarUrl = AppPreferences.getUserInfo().avatar
        })
        list.add(FavoriteAdapter.ItemDisplay().apply {
            count = 42
            itemList = listOf(
                Item(thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg"),
                Item(thumbnail = "https://i.pinimg.com/564x/a3/0d/94/a30d9464d035e1f30dd088d2ba89103d.jpg"),
                Item(thumbnail = "https://i.pinimg.com/564x/e4/78/fa/e478fae5d5ecf6f8537f248f6cab0d15.jpg"),
                Item(thumbnail = "https://i.pinimg.com/564x/75/bd/2e/75bd2e6f99d705642531b3f214ff4f70.jpg")
            )
        })
        list.add(FavoriteAdapter.StoryDisplay().apply {
            count = 30
            storyList = listOf(
                Story(
                    thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg",
                    title = "Story 1",
                    description = "Description 1"
                ),
                Story(
                    thumbnail = "https://i.pinimg.com/564x/a3/0d/94/a30d9464d035e1f30dd088d2ba89103d.jpg",
                    title = "Story 2",
                    description = "Description 2"
                ),
                Story(
                    thumbnail = "https://i.pinimg.com/564x/e4/78/fa/e478fae5d5ecf6f8537f248f6cab0d15.jpg",
                    title = "Story 3",
                    description = "Description 3"
                ),
                Story(
                    thumbnail = "https://i.pinimg.com/564x/75/bd/2e/75bd2e6f99d705642531b3f214ff4f70.jpg",
                    title = "Story 4",
                    description = "Description 4"
                ),
                Story(
                    thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg",
                    title = "Story 5",
                    description = "Description 5"
                ),
                Story(
                    thumbnail = "https://i.pinimg.com/564x/a3/0d/94/a30d9464d035e1f30dd088d2ba89103d.jpg",
                    title = "Story 6",
                    description = "Description 6"
                ),
            )
        })
        list.add(FavoriteAdapter.CollectionDisplay().apply {
            count = 13
            collectionList = listOf(
                MCollection(thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg"),
                MCollection(thumbnail = "https://i.pinimg.com/564x/a3/0d/94/a30d9464d035e1f30dd088d2ba89103d.jpg"),
                MCollection(thumbnail = "https://i.pinimg.com/564x/e4/78/fa/e478fae5d5ecf6f8537f248f6cab0d15.jpg"),
                MCollection(thumbnail = "https://i.pinimg.com/564x/75/bd/2e/75bd2e6f99d705642531b3f214ff4f70.jpg"),
                MCollection(thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg"),
                MCollection(thumbnail = "https://i.pinimg.com/564x/a3/0d/94/a30d9464d035e1f30dd088d2ba89103d.jpg")
            )
        })
        return list
    }
}
