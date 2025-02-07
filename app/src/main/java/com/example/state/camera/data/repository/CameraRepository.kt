package com.example.state.camera.data.repository

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import com.example.state.camera.data.datasource.CameraService
import com.example.state.camera.data.model.CameraCaptureResult
import android.provider.MediaStore
import android.content.Context
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import java.text.SimpleDateFormat
import java.util.*

class CameraRepository(private val cameraService: CameraService, private val context: Context) {

    fun startCamera(cameraProvider: ProcessCameraProvider, preview: Preview) {
        try {
            // Iniciar la cámara
            cameraService.startCamera(cameraProvider, preview)
        } catch (e: Exception) {
            Log.e("CameraRepository", "Error al iniciar la cámara: ${e.message}")
        }
    }

    fun captureImage(): CameraCaptureResult {
        var result = CameraCaptureResult("", false)

        try {
            // Crear archivo de salida
            val outputFileOptions = createOutputFileOptions()

            // Intentar capturar la imagen
            cameraService.captureImage(outputFileOptions, object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    // Éxito, actualizamos el resultado
                    result = CameraCaptureResult(outputFileResults.savedUri.toString(), true)
                    Log.d("CameraRepository", "Imagen guardada en: ${outputFileResults.savedUri}")
                }

                override fun onError(exception: ImageCaptureException) {
                    // Error al capturar
                    result = CameraCaptureResult("", false)
                    Log.e("CameraRepository", "Error al capturar la imagen: ${exception.message}")
                }
            })
        } catch (e: Exception) {
            result = CameraCaptureResult("", false)
            Log.e("CameraRepository", "Error en la captura de la imagen: ${e.message}")
        }

        return result
    }

    // Crear el archivo de salida para la imagen
    private fun createOutputFileOptions(): ImageCapture.OutputFileOptions {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val fileName = "JPEG_$timeStamp.jpg"

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        val uri: Uri? = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            return ImageCapture.OutputFileOptions.Builder(context.contentResolver, it, contentValues).build()
        }

        throw Exception("No se pudo crear el archivo de salida para la imagen")
    }
}


