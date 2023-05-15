package com.tiger.ar.museum.presentation.streetview.list

import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.domain.model.StreetView
import kotlinx.coroutines.launch

class AllStreetViewModel : BaseViewModel() {
    val countAndStreetViews: MutableList<Any> = mutableListOf()
    var count: Long? = null
    private var lastVisibleName: String? = null

    fun getAllStreetView(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {

            if (countAndStreetViews.size.toLong() + 1 == count) {
                return@launch
            }

            val db = Firebase.firestore
            val streetViewsRef = db.collection("home/md5P7vYwbF1E7BJzHnaM/streetViews").orderBy("name")

            if (count == null) {
                val countQuery = streetViewsRef.count()
                countQuery.get(AggregateSource.SERVER).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val snapshot = task.result
                        count = snapshot.count
                        countAndStreetViews.add(0, count!!)
                        getStreetViewList(streetViewsRef, onSuccess, onFailure)
                    } else {
                        onFailure(task.exception?.message ?: "Unknown error")
                    }

                    if (count == null) {
                        count = 0
                    }
                }
            } else {
                getStreetViewList(streetViewsRef, onSuccess, onFailure)
            }


        }
    }

    var isStop = false
    val MAX_ITEM_PER_PAGE = 3L

    private fun getStreetViewList(streetViewsOrderByNameQuery: Query, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (isStop) {
            onSuccess.invoke()
            return
        }
        val query = if (lastVisibleName == null) {
            streetViewsOrderByNameQuery.limit(MAX_ITEM_PER_PAGE)
        } else {
            streetViewsOrderByNameQuery.startAfter(lastVisibleName).limit(MAX_ITEM_PER_PAGE)
        }

        query.get().addOnSuccessListener { streetViewsSnapshot ->

            if (streetViewsSnapshot.size() < MAX_ITEM_PER_PAGE) {
                isStop = true
            }

            lastVisibleName = streetViewsSnapshot.documents.lastOrNull()?.toObject(StreetView::class.java)?.name

            countAndStreetViews.addAll(streetViewsSnapshot.documents.mapNotNull {
                it.toObject(StreetView::class.java)?.apply { key = it.id }
            })

            onSuccess.invoke()
        }
    }
}
