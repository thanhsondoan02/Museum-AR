package com.tiger.ar.museum.presentation.camera.view3d.control

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.domain.model.Item

class View3dControllerViewModel : BaseViewModel() {
    var listItem: List<View3dControllerAdapter.ItemDisplay> = emptyList()

    fun getListItem(onSuccessAction: () -> Unit, onFailAction: (String) -> Unit) {
        val db = Firebase.firestore
        val itemsRef = db.collection("items")
        val query = itemsRef.whereNotEqualTo("model3d", null)
        query.get().addOnSuccessListener {
            listItem = it.documents.mapNotNull { itemSnapshot ->
                val item = itemSnapshot.toObject(Item::class.java)?.apply { key = itemSnapshot.id }
                var itemDisplay: View3dControllerAdapter.ItemDisplay? = null
                if (item != null) {
                    itemDisplay = View3dControllerAdapter.ItemDisplay(item)
                }
                itemDisplay
            }
            onSuccessAction.invoke()
        }.addOnFailureListener {
            onFailAction.invoke(it.message ?: "Unknown error")
        }
    }
}
