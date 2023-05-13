package com.tiger.ar.museum.presentation.camera.view3d

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.google.ar.sceneform.rendering.ModelRenderable
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.common.extension.getApplication
import java.io.File

class View3dViewModel : BaseViewModel() {
    var model3d: ModelRenderable? = null
    var tempFile: File? = null

    fun getFileFromUrl(url: String?, onStartAction: () -> Unit, onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        if (url == null) {
            onFailureAction.invoke("Url is null")
            return
        }
//        val url = "https://culturalinstitute-3d-serving.storage.googleapis.com/240391958/_QETQaU_ZZSf1g/1625055444215/gltf/model.glb"
//        val url = "https://firebasestorage.googleapis.com/v0/b/museum-ar-32277.appspot.com/o/trash_can.glb?alt=media&token=2423a161-0d0e-4a99-be9e-b82cada39824"
        val fileName = "suck.glb"

        val downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val file = File(downloadDirectory, fileName)
        if (file.exists() && !file.delete()) {
            onFailureAction.invoke("Delete file failed")
            return
        }
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Downloading GLB file")
            .setDescription("Downloading $fileName")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

//        tempFile = File(getApplication().filesDir, fileName)
//        request.setDestinationUri(Uri.fromFile(tempFile))
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        val downloadManager = getApplication().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        onStartAction.invoke()
        val downloadId = downloadManager.enqueue(request)
        this.tempFile = file
    }
}
