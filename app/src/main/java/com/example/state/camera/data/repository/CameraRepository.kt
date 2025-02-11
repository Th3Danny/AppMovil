package com.example.state.camera.data.repository

import android.content.Context
import com.example.state.camera.data.datasource.CameraService
import android.net.Uri
class CameraRepository(private val cameraService: CameraService) {


    fun getPhotoUri(context: Context): Uri? {
        val photoData = cameraService.getPhotoUri(context) // Se pasa el contexto aquí
        return photoData?.first // Extrae solo la URI del Pair<Uri, String>
    }
}



