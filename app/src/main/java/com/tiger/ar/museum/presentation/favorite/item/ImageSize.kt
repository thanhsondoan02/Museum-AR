package com.tiger.ar.museum.presentation.favorite.item

import com.tiger.ar.museum.domain.model.Item

data class ImageSize(
    val height: Int,
    val width: Int
)

class ItemDisplay {
    var item: Item? = null
    var imageSize: ImageSize? = null
}
