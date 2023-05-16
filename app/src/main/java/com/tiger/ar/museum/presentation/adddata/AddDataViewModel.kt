package com.tiger.ar.museum.presentation.adddata

import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.common.extension.getApplication
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.domain.model.Item
import com.tiger.ar.museum.domain.model.Page
import com.tiger.ar.museum.domain.model.Story
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class AddDataViewModel : BaseViewModel() {

    var itemList: List<Item> = listOf()
    var streetViewList: List<StreetView> = listOf()
    var storyList: List<Story> = listOf()

    fun addData(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        viewModelScope.launch {
            mapItemList()
            performAddToDatabase(onSuccessAction, onFailureAction)
        }
    }

    data class StreetViewGson(
        var name: String? = null,

        var lat: Double? = null,

        var lng: Double? = null,

        var thumbnail: String? = null
    )

    data class StreetView(
        var name: String? = null,

        var location: GeoPoint? = null,

        var thumbnail: String? = null
    )

    fun addStreetViewList() {
        viewModelScope.launch {
            val inputStream = getApplication().resources.openRawResource(R.raw.street_view_1)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val json = reader.readText()
            streetViewList = Gson().fromJson(json, Array<StreetViewGson>::class.java).toList().map {
                StreetView(
                    name = it.name,
                    location = GeoPoint(it.lat ?: 0.0, it.lng ?: 0.0),
                    thumbnail = it.thumbnail
                )
            }

            val db = Firebase.firestore
            val batch = db.batch()

            streetViewList.take(1).forEach {
                val docRef = db.collection("home/md5P7vYwbF1E7BJzHnaM/streetViews").document()
                batch.set(docRef, it)
            }

            batch.commit()
                .addOnSuccessListener { toast("Success") }
                .addOnFailureListener { toast("Failure") }
        }
    }

    data class GsonStory(
        var name: String? = null,

        var desc: String? = null,

        var thumbnail: String? = null,

        var pages: List<GsonPage>? = null
    )

    data class GsonPage(
        var desc: String? = null,

        var thumbnail: String? = null
    )

    fun addStoryList() {
        viewModelScope.launch {
            val inputStream = getApplication().resources.openRawResource(R.raw.story_1)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val json = reader.readText()
            storyList = Gson().fromJson(json, Array<GsonStory>::class.java).toList().mapNotNull { gsonStory ->

                val pages = gsonStory.pages?.toMutableList()

                pages?.removeIf { it.thumbnail.isNullOrEmpty() || it.desc.isNullOrEmpty() }

                Story(
                    title = gsonStory.name,
                    description = gsonStory.desc,
                    thumbnail = gsonStory.thumbnail,
                    pages = pages?.map {
                        Page(
                            description = it.desc,
                            thumbnail = "https:" + it.thumbnail
                        )
                    },
                    collectionId = "aCziNZYzC09cCeqizUAv"
                )
            }

            val db = Firebase.firestore
            val batch = db.batch()

            storyList.forEach {
                val docRef = db.collection("stories").document()
                batch.set(docRef, it)
            }

            batch.commit()
                .addOnSuccessListener { toast("Success") }
                .addOnFailureListener { toast("Failure") }
        }
    }

    private fun performAddToDatabase(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        val db = Firebase.firestore
        val batch = db.batch()

        itemList.forEach {
            val docRef = db.collection("items").document()
            batch.set(docRef, it)
        }

        batch.commit()
            .addOnSuccessListener { onSuccessAction.invoke() }
            .addOnFailureListener { onFailureAction.invoke(it.message ?: "Failure") }
    }

    private fun mapItemList() {
        val inputStream = getApplication().resources.openRawResource(R.raw.data1)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val json = reader.readText()
        this.itemList = Gson().fromJson(json, Array<Item>::class.java).toList()
    }
}
