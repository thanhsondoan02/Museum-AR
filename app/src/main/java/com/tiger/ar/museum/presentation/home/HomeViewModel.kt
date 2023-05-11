package com.tiger.ar.museum.presentation.home

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.domain.model.HomeData
import com.tiger.ar.museum.domain.model.StreetView

class HomeViewModel : BaseViewModel() {
    var homeData: HomeData? = null
    var list = mutableListOf<Any>()

    fun getHomeData(onSuccessAction: () -> Unit) {
        val homeDataRef = Firebase.firestore
            .collection("home")
            .orderBy(FieldPath.documentId())
            .limit(1)
        homeDataRef.get().addOnSuccessListener {
            this.homeData = it.documents.firstOrNull()?.toObject(HomeData::class.java)

            // get street view list
            val streetViewRef = Firebase.firestore
                .collection("home/${it.documents.first().id}/streetViews")
                .orderBy(FieldPath.documentId())
                .limit(4)
            streetViewRef.get().addOnSuccessListener { svSnapShot ->
                homeData?.streetViews = svSnapShot.documents.mapNotNull { document ->
                    document.toObject(StreetView::class.java)
                }
                list.add(HomeAdapter.StreetViewDisplay().apply { streetViews = homeData?.streetViews })
                onSuccessAction()
            }
        }
    }
}
