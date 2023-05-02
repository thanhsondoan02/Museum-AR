package com.tiger.ar.museum.domain.model

import com.tiger.ar.museum.AppPreferences
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(

    var name: String? = null,

    var thumbnail: String? = null,

    var time: String? = null,

    var streetView: String? = null,

    var model3d: String? = null,

    var collectionId: String? = null,

    var collection: MCollection? = null,

    var creatorId: String? = null,

    var creator: Creator? = null,

    @Deprecated("Use fun isLiked() key instead")
    var isLiked: Boolean? = null

    ) : MuseumModel() {

    fun safeIsLiked(): Boolean {
        if (isLiked == null) {
            mapIsLiked()
        }
        return isLiked ?: false
    }

    fun mapIsLiked() {
        isLiked = AppPreferences.getUserInfo().fitems?.contains(key) ?: false
    }
}
