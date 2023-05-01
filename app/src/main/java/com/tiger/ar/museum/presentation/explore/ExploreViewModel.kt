package com.tiger.ar.museum.presentation.explore

import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.domain.model.Exhibition
import kotlinx.coroutines.launch

class ExploreViewModel: BaseViewModel() {
    private var exhibitions = listOf<Exhibition>()

    fun getExhibitionData(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        viewModelScope.launch {
            if (AppPreferences.getUserInfo().key == null) {
                onFailureAction("User key is null")
                return@launch
            }
            val exhibitionRef = Firebase.firestore
                .collection("exhibitions")
                .whereGreaterThan("endTime", Timestamp.now())
            exhibitionRef.get()
                .addOnSuccessListener {
                    exhibitions = (it.documents).mapNotNull { exhibition ->
                        exhibition.toObject(Exhibition::class.java)?.apply { key = exhibition.id }
                    }
                    onSuccessAction.invoke()
                }
                .addOnFailureListener {
                    onFailureAction.invoke(getAppString(R.string.fail) + ": ${it.message}")
                }
        }
    }

    fun getExploreData(): List<Any> {
        return exhibitions
    }
}
