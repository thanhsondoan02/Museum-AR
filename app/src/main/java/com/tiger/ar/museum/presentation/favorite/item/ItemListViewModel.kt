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
    companion object {
        const val VERTICAL_RATIO = 4 / 3f
    }

    var items: List<Item> = emptyList()
    var itemDisplays: MutableList<ItemDisplay> = mutableListOf()

    private var count = 0

    fun calculateSizeOfListImage(onSuccess: () -> Unit) {
        viewModelScope.launch {
            loadImageUrlListWithGlide(onSuccess)
        }
    }

    private fun loadImageUrlListWithGlide(onSuccess: () -> Unit) {
        count = items.size
        var hasLeft = false
        for (item in items) {
            Glide.with(getApplication()).load(item.thumbnail).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        count--
                        if (count == 0) {
                            onSuccess.invoke()
                        }
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        count--
                        if (resource != null) {
                            val width = resource.intrinsicWidth
                            val height = resource.intrinsicHeight
                            itemDisplays.add(ItemDisplay().apply {
                                this.item = item
                                this.imageSize = ImageSize(height, width)
                                if (height / width.toFloat() > VERTICAL_RATIO) {
                                    if (hasLeft) {
                                        itemDisplays.last().countInRow = 2
                                        this.countInRow = 2
                                        hasLeft = false

                                        val ratio = itemDisplays.last().imageSize.width / this.imageSize.width.toFloat()

                                    } else {
                                        hasLeft = true
                                    }
                                } else {
                                    hasLeft = false
                                }
                            })
                        }
                        if (count == 0) {
                            onSuccess.invoke()
                        }
                        return false
                    }
                }).submit()
        }
    }
}
