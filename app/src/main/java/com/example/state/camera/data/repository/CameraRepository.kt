package com.example.state.camera.data.repository

import android.content.Context
import android.util.Log
import com.example.state.camera.data.datasource.CameraService
import com.example.state.camera.data.model.CameraCaptureResult
import android.net.Uri


class CameraRepository(private val cameraService: CameraService) {

    fun captureImage(): CameraCaptureResult {
        return try {
            val uri = cameraService.takePicture()
            if (uri != null) {
                Log.d("CameraRepository", "Imagen capturada correctamente en: $uri")
                CameraCaptureResult(uri.toString(), true)
            } else {
                CameraCaptureResult("", false)
            }
        } catch (e: Exception) {
            Log.e("CameraRepository", "Error en la captura de la imagen: ${e.message}")
            CameraCaptureResult("", false)
        }
    }

    // ✅ Corregimos esta función para requerir el contexto como parámetro
    fun getPhotoUri(context: Context): Uri? {
        val photoData = cameraService.getPhotoUri(context) // Se pasa el contexto aquí
        return photoData?.first // Extrae solo la URI del Pair<Uri, String>
    }
}



