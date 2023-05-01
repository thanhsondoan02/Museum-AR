package com.tiger.ar.museum.presentation.explore

import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.domain.model.Exhibition
import com.tiger.ar.museum.domain.model.Item
import kotlinx.coroutines.launch

class ExploreViewModel : BaseViewModel() {
    var exploreData: List<Any> = listOf()

    private var exhibitions = listOf<Exhibition>()
    private var items = listOf<Item>()

    fun getExploreDataFromDatabase(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        viewModelScope.launch {
            val exhibitionRef = Firebase.firestore
                .collection("exhibitions")
                .whereGreaterThan("endTime", Timestamp.now()).get()

            val itemRef = Firebase.firestore
                .collection("items").get()

            Tasks.whenAllSuccess<QuerySnapshot>(exhibitionRef, itemRef)
                .continueWith {
                    exhibitions = (exhibitionRef.result.documents).mapNotNull { exhibition ->
                        exhibition.toObject(Exhibition::class.java)?.apply { key = exhibition.id }
                    }
                    items = (itemRef.result.documents).mapNotNull { item ->
                        item.toObject(Item::class.java)?.apply { key = item.id }
                    }
                    onSuccessAction.invoke()
                }
                .addOnFailureListener {
                    onFailureAction.invoke(getAppString(R.string.fail) + ": ${it.message}")
                }
        }
    }

    fun getShuffledExploreData(): List<Any> {
        val list = mutableListOf<Any>()
        list.addAll(exhibitions)
        list.addAll(items)
        val list2 = mutableListOf<Any>()
        while (list.isNotEmpty()) {
            val randomIndex = (0 until list.size).random()
            list2.add(list[randomIndex])
            list.removeAt(randomIndex)
        }
        exploreData = list2
        return exploreData
    }
}
