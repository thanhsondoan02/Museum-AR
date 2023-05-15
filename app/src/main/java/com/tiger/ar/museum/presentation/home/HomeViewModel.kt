package com.tiger.ar.museum.presentation.home

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.domain.model.HomeData
import com.tiger.ar.museum.domain.model.MCollection
import com.tiger.ar.museum.domain.model.StreetView

class HomeViewModel : BaseViewModel() {
    var homeData: HomeData? = null
    var list = mutableListOf<Any>()

    fun getHomeData(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        val homeDataRef = Firebase.firestore
            .collection("home")
            .orderBy(FieldPath.documentId())
            .limit(1)
        homeDataRef.get().addOnSuccessListener { homeSnapShot ->
            this.homeData = homeSnapShot.documents.firstOrNull()?.toObject(HomeData::class.java)

            val listTask = mutableListOf<Task<QuerySnapshot>>()

            val streetViewRef = Firebase.firestore
                .collection("home/${homeSnapShot.documents.first().id}/streetViews")
                .whereNotEqualTo("place", null)
                .limit(4).get()

            val collectionRef = Firebase.firestore
                .collection("collections")
                .orderBy(FieldPath.documentId())
                .limit(5).get()

            listTask.add(streetViewRef)
            listTask.add(collectionRef)

            Tasks.whenAllSuccess<QuerySnapshot>(listTask)
                .continueWith {
                    // collection
                    homeData?.collections = collectionRef.result.documents.mapNotNull { document ->
                        document.toObject(MCollection::class.java)?.apply { key = document.id }
                    }
                    list.add(HomeAdapter.CollectionDisplay().apply { collections = homeData?.collections })

                    // street view
                    homeData?.streetViews = streetViewRef.result.documents.mapNotNull { document ->
                        document.toObject(StreetView::class.java)
                    }
                    list.add(HomeAdapter.StreetViewDisplay().apply { streetViews = homeData?.streetViews })

                    onSuccessAction.invoke()
                }
                .addOnFailureListener {
                    onFailureAction.invoke(it.message ?: "Error")
                }
        }
    }
}
