package com.tiger.ar.museum.presentation.collection

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.domain.model.MCollection
import com.tiger.ar.museum.domain.model.User

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
                val collection = it.toObject(MCollection::class.java)?.apply { key = it.id }

                if (collection != null) {
                    list.add(CollectionAdapter.HeaderDisplay().apply {
                        this.collection = collection
                    })
                    list.add(CollectionAdapter.DescriptionDisplay(collection.description))
                    list.add(CollectionAdapter.StoriesDisplay(collectionId))
                    list.add(CollectionAdapter.ItemsDisplay(collectionId))
                    onSuccessAction.invoke()
                } else {
                    onFailureAction.invoke("Collection is null")
                }
            }
            .addOnFailureListener {
                onFailureAction.invoke(it.message ?: "Get collection failed")
            }
    }

    fun follow(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        updateFollowInDatabase(true, onSuccessAction, onFailureAction)
    }

    fun unFollow(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        updateFollowInDatabase(false, onSuccessAction, onFailureAction)
    }

    private fun updateFollowInDatabase(isAdd: Boolean, onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        if (AppPreferences.getUserInfo().key == null) {
            throw Exception("User key is null")
        }
        if (collectionId == null) {
            throw Exception("Collection id is null")
        }

        val followRef = Firebase.firestore.collection("users")
            .document(AppPreferences.getUserInfo().key!!)

        val followTask: Task<Void> = if (isAdd) {
            followRef.update("fcollections", FieldValue.arrayUnion(collectionId!!))
        } else {
            followRef.update("fcollections", FieldValue.arrayRemove(collectionId!!))
        }

        followTask.addOnSuccessListener {
            Firebase.firestore
                .collection("users")
                .document(AppPreferences.getUserInfo().key!!).get()
                .addOnSuccessListener {
                    val user = it.toObject(User::class.java)?.apply { key = it.id }
                    AppPreferences.setUserInfo(user!!)

                    val newHeaderDisplay = (list.first() as CollectionAdapter.HeaderDisplay).copy()
                    list[0] = newHeaderDisplay
                    onSuccessAction.invoke()
                }
                .addOnFailureListener {
                    onFailureAction.invoke(it.message ?: "Follow/Unfollow failed")
                }
        }.addOnFailureListener {
            onFailureAction.invoke(it.message ?: "Follow/Unfollow failed")
        }
    }
}
