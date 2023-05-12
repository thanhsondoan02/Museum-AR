package com.tiger.ar.museum.presentation.collection

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.domain.model.MCollection

class CollectionViewModel : BaseViewModel() {
    var collectionId: String? = null
    var list = mutableListOf<Any>()

    fun getCollectionData(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        if (collectionId == null) {
            onFailureAction.invoke("Collection id is null")
        }

        list.clear()

        val collectionRef = Firebase.firestore.collection("collections")
            .document(collectionId!!)

        collectionRef.get()
            .addOnSuccessListener {
                val collection = it.toObject(MCollection::class.java)

                if (collection != null) {
                    list.add(CollectionAdapter.HeaderDisplay().apply {
                        this.collection = collection
                    })
                    onSuccessAction.invoke()
                } else {
                    onFailureAction.invoke("Collection is null")
                }
            }
            .addOnFailureListener {
                onFailureAction.invoke(it.message ?: "Get collection failed")
            }
    }
}
