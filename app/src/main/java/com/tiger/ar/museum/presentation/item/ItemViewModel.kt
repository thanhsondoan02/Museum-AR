package com.tiger.ar.museum.presentation.item

import androidx.lifecycle.viewModelScope
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.domain.model.Item
import com.tiger.ar.museum.domain.model.mockItem
import kotlinx.coroutines.launch

class ItemViewModel : BaseViewModel() {
    var item: Item? = mockItem()
    var itemData: MutableList<Any> = mutableListOf()

    fun mapDataForAdapter(onSuccess: () -> Unit) {
        viewModelScope.launch {
            itemData.clear()

            itemData.add(ItemAdapter.TransparentDisplay())

            itemData.add(ItemAdapter.ChooseActionDisplay().apply {
                val actionList = mutableListOf(ItemActionAdapter.ActionDisplay(ACTION_TYPE.ZOOM_IN))
                if (item?.model3d != null) {
                    actionList.add(ItemActionAdapter.ActionDisplay(ACTION_TYPE.AR))
                }
                if (item?.streetView != null) {
                    actionList.add(ItemActionAdapter.ActionDisplay(ACTION_TYPE.STREET))
                }
                actions = actionList
            })

            itemData.add(ItemAdapter.TitleDisplay().apply {
                title = item?.name
                creator = item?.creator?.name
                time = item?.time
                collectionName = item?.collection?.name
                collectionThumb = item?.collection?.thumbnail
                isLike = item?.safeIsLiked() ?: false
            })

            itemData.add(ItemAdapter.DescriptionDisplay().apply {
                description = item?.description
            })

            itemData.add(ItemAdapter.DetailTitleDisplay().apply {
                isOpen = false
            })

            itemData.add(ItemAdapter.RecommendDisplay())

            onSuccess.invoke()
        }
    }
}
