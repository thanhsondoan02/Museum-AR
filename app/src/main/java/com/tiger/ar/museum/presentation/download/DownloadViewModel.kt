package com.tiger.ar.museum.presentation.download

import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tiger.ar.museum.common.BaseViewModel
import kotlinx.coroutines.launch

class DownloadViewModel : BaseViewModel() {
    var dataList: List<DownloadAdapter.DownloadItem>? = null

    fun getDownloadItemDataFromDb(onSuccessAction: () -> Unit) {
        viewModelScope.launch {
            val db = Firebase.firestore
            val itemsRef = db.collection("items")
            itemsRef.get().addOnSuccessListener {  itemsSnapshot ->
                dataList = itemsSnapshot.mapNotNull {
                    it.toObject(DownloadAdapter.DownloadItem::class.java).apply { id = it.id }
                }

                onSuccessAction.invoke()
            }
        }
    }
}
