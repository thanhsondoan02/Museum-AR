package com.tiger.ar.museum.presentation.download2

data class DownloadFile(
    var id: Int? = null,
    var url: String? = null,
    var percent: Int? = null,
    var name: String? = null,
    var status: Int? = null,
    var downloadId: Long? = null
)
