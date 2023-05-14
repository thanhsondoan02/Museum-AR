package com.tiger.ar.museum.presentation.camera.view3d

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.viewModelScope
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.common.extension.getApplication
import com.tiger.ar.museum.domain.model.Item
import com.tiger.ar.museum.presentation.camera.view3d.control.View3dControllerAdapter
import kotlinx.coroutines.launch
import java.io.File

class View3dViewModel : BaseViewModel() {
    var modelRenderable: ModelRenderable? = null
    var tempFile: File? = null
    var listItem: MutableList<View3dControllerAdapter.ItemDisplay> = mutableListOf()
    var downloadMap: MutableMap<Long, String> = mutableMapOf()

    fun updateItemDownloadState(itemKey: String?, downloadStatus: DOWNLOAD_STATUS, onSuccessAction: () -> Unit) {
        viewModelScope.launch {
            val index = listItem.indexOfFirst { it.item.key == itemKey }
            if (index == -1) {
                throw Exception("Item not found")
            }
            val oldItem = listItem[index]
            listItem[index] = View3dControllerAdapter.ItemDisplay(oldItem.item, downloadStatus)
            onSuccessAction.invoke()
        }
    }

    fun getFileFromUrl(item: Item, onStartAction: () -> Unit, onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        val url = item.model3d
        if (url == null) {
            onFailureAction.invoke("Url is null")
            return
        }

        val fileName = item.key + ".glb"

        val downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val file = File(downloadDirectory, fileName)
        if (file.exists() && !file.delete()) {
            onFailureAction.invoke("Delete file failed")
            return
        }
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Downloading GLB file")
            .setDescription("Downloading $fileName")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)

//        tempFile = File(getApplication().filesDir, fileName)
//        request.setDestinationUri(Uri.fromFile(tempFile))
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        val downloadManager = getApplication().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        onStartAction.invoke()
        val downloadId = downloadManager.enqueue(request)
        downloadMap[downloadId] = item.key!!
    }

    fun getFileFromDevice(itemId: String): File {
        val fileName = "$itemId.glb"
        val downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        return File(downloadDirectory, fileName)
    }

    fun isFileExist(itemId: String): Boolean {
        val file = getFileFromDevice(itemId)
        return file.exists()
    }

    fun getListItem(onSuccessAction: () -> Unit, onFailAction: (String) -> Unit) {
        viewModelScope.launch {
            val db = Firebase.firestore
            val itemsRef = db.collection("items")
            val query = itemsRef.whereNotEqualTo("model3d", null)
            query.get().addOnSuccessListener {
                listItem = it.documents.mapNotNull { itemSnapshot ->
                    val item = itemSnapshot.toObject(Item::class.java)?.apply { key = itemSnapshot.id }
                    var itemDisplay: View3dControllerAdapter.ItemDisplay? = null
                    if (item?.key != null) {
                        itemDisplay = View3dControllerAdapter.ItemDisplay(item)
                        if (isFileExist(item.key!!)) {
                            itemDisplay.downloadStatus = DOWNLOAD_STATUS.DOWNLOADED
                        }
                    }
                    itemDisplay
                }.toMutableList()
                onSuccessAction.invoke()
            }.addOnFailureListener {
                onFailAction.invoke(it.message ?: "Unknown error")
            }
        }
    }

    fun buildModel(itemId: String?, onStartAction: () -> Unit, onSuccessAction: () -> Unit, onFailAction: (String) -> Unit) {
        if (itemId == null) {
            onFailAction.invoke("itemId is null")
            return
        }
        onStartAction.invoke()
        this.tempFile = getFileFromDevice(itemId)
        val renderableSource = RenderableSource
            .builder()
            .setSource(getApplication(), Uri.parse(tempFile?.path), RenderableSource.SourceType.GLB)
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()
        ModelRenderable
            .builder()
            .setSource(getApplication(), renderableSource)
            .setRegistryId(tempFile?.path)
            .build()
            .thenAccept { modelRenderable: ModelRenderable? ->
                if (modelRenderable != null) {
                    this.modelRenderable = modelRenderable
                    onSuccessAction.invoke()
                } else {
                    onFailAction.invoke("modelRenderable is null")
                }
            }
    }
}
