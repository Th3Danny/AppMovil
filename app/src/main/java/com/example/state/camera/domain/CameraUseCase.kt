package com.example.state.camera.domain

import android.content.Context
import android.net.Uri
import com.example.state.camera.data.datasource.CameraService
import com.example.state.camera.data.repository.CameraRepository
import java.io.File


class CameraUseCase(private val cameraRepository: CameraRepository) {

    // ✅ Ahora recibe context como parámetro y lo pasa al repositorio
    fun getPhotoUri(context: Context): Pair<Uri, String>? {
        return cameraRepository.getPhotoUri(context)?.let { uri ->
            val filePath = File(uri.path!!).absolutePath
            Pair(uri, filePath)
        }
    }
}





