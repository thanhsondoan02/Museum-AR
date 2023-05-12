package com.tiger.ar.museum.domain.model

import com.tiger.ar.museum.AppPreferences
import kotlinx.parcelize.Parcelize

@Parcelize
data class MCollection(

    var name: String? = null,

    var thumbnail: String? = null,

    var place: String? = null,

    var description: String? = null,

    var icon: String? = null,

    @Deprecated("Use fun safeIsLiked() key instead")
    var isLiked: Boolean? = null

) : MuseumModel() {

    fun safeIsLiked(): Boolean {
        if (isLiked == null) {
            mapIsLiked()
        }
        return isLiked ?: false
    }

    fun mapIsLiked() {
        isLiked = AppPreferences.getUserInfo().fcollections?.contains(key) ?: false
    }

}

fun mockListCollection(): List<MCollection> {
    val list: MutableList<MCollection> = mutableListOf()
    for (i in 0 until 100) {
        list.add(
            MCollection(
                name = "Collection $i",
                thumbnail = listOf(
                    "https://firebasestorage.googleapis.com/v0/b/museum-ar-32277.appspot.com/o/mock%20server%2Fcollection%20images%2Ffacebook_1677293945972_7035080706770265309.jpg?alt=media&token=5a51bb73-8b69-4ec5-9de1-4b9909dedc55.\n",
                    "https://firebasestorage.googleapis.com/v0/b/museum-ar-32277.appspot.com/o/mock%20server%2Fcollection%20images%2F2ae6491a7f360fb67b560959213aa6cc.jpg?alt=media&token=64ff4f7f-9baf-44ec-895a-d9d088c00811",
                    "https://firebasestorage.googleapis.com/v0/b/museum-ar-32277.appspot.com/o/mock%20server%2Fcollection%20images%2F5bcab78b49abf2e465ebc085a4dc9fde.jpg?alt=media&token=28ca91b2-d9c2-4785-845c-2109fe81dbb0",
                    "https://firebasestorage.googleapis.com/v0/b/museum-ar-32277.appspot.com/o/mock%20server%2Fcollection%20images%2Ffacebook_1667729415449_6994964158135719728.jpg?alt=media&token=fbf0c366-381c-49fc-9f73-e76fbffdd3bd",
                    "https://firebasestorage.googleapis.com/v0/b/museum-ar-32277.appspot.com/o/mock%20server%2Fcollection%20images%2Ffacebook_1677293945972_7035080706770265309.jpg?alt=media&token=5a51bb73-8b69-4ec5-9de1-4b9909dedc55"
                ).random()
            )
        )
    }
    return list
}
