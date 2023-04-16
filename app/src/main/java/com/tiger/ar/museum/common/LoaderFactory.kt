package com.tiger.ar.museum.common

object LoaderFactory {
    private val imageLoader = GlideImageLoaderImpl()

    fun glide(): IImageLoader {
        return imageLoader
    }
}
