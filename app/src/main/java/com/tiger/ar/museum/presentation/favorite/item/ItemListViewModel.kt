package com.tiger.ar.museum.presentation.favorite.item

import android.graphics.drawable.Drawable
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.common.extension.getApplication
import com.tiger.ar.museum.domain.model.Item
import kotlinx.coroutines.launch

class ItemListViewModel : BaseViewModel() {
    var items: List<Item> = emptyList()
    var imageUrlList: List<String> = emptyList()
    var imageSizeList: MutableList<ImageSize> = mutableListOf()

    fun calculateSizeOfListImage(onSuccess: () -> Unit) {
        viewModelScope.launch {
            mapImageList()

            for (imageUrl in imageUrlList) {
                Glide.with(getApplication())
                    .load(imageUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            if (resource != null) {
                                val width = resource.intrinsicWidth
                                val height = resource.intrinsicHeight
                                imageSizeList.add(ImageSize(height, width))
                            }
                            return false
                        }
                    })
                    .submit()
            }
            onSuccess.invoke()
        }
    }

    private fun mapImageList() {
        imageUrlList = items.map { it.thumbnail ?: "" }
    }
}
