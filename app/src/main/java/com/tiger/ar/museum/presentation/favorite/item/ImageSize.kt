package com.tiger.ar.museum.presentation.favorite.item

import com.tiger.ar.museum.domain.model.Item

data class ImageSize(
    val height: Int,
    val width: Int
)

class ItemDisplay {
    var item: Item? = null
    var imageSize: ImageSize = ImageSize(1, 1)
    var countInRow: Int = 1
    var ratio: Float = 1f
}
