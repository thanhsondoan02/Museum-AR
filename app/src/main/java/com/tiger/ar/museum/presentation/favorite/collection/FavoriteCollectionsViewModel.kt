package com.tiger.ar.museum.presentation.favorite.collection

import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.domain.model.MCollection

class FavoriteCollectionsViewModel : BaseViewModel() {
    var collections: List<MCollection> = emptyList()
}
