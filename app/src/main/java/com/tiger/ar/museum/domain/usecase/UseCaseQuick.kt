package com.tiger.ar.museum.domain.usecase

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.domain.model.User

fun updateLikeItem(isLike: Boolean, itemId: String?, onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
    if (itemId == null) {
        onFailureAction.invoke("Item key is null")
        return
    }

    val userRef = Firebase.firestore.collection("users").document(AppPreferences.getUserId())

    val updateLikeTask = if (isLike) {
        userRef.update("fitems", FieldValue.arrayUnion(itemId))
    } else {
        userRef.update("fitems", FieldValue.arrayRemove(itemId))
    }

    updateLikeTask.addOnSuccessListener {
        userRef.get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)?.apply { key = it.id }
                AppPreferences.setUserInfo(user!!)
                onSuccessAction.invoke()
            }.addOnFailureListener {
                onFailureAction.invoke(it.message ?: "Like item failed")
            }
    }.addOnFailureListener {
        onFailureAction.invoke(it.message ?: "Like item failed")
    }
}


