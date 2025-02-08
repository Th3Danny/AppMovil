package com.example.state.camera.data.datasource

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File

class CameraService(private val context: Context) {

    private var imageCapture: ImageCapture? = null

    fun getImageCapture(): ImageCapture {
        return imageCapture ?: run {
            // Inicializa ImageCapture si aún no está configurado
            imageCapture = ImageCapture.Builder().build()
            imageCapture!!
        }
    }

    fun startCamera(
        lifecycleOwner: LifecycleOwner,
        cameraProvider: ProcessCameraProvider,
        preview: Preview,
        previewView: PreviewView
    ) {
        try {
            cameraProvider.unbindAll()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            // Configura y vincula ImageCapture
            imageCapture = ImageCapture.Builder().build()

            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture // Vincula ImageCapture
            )

            preview.setSurfaceProvider(previewView.surfaceProvider)

            Log.d("CameraService", "Cámara vinculada exitosamente")
        } catch (e: Exception) {
            Log.e("CameraService", "Error al iniciar la cámara: ${e.message}")
        }
    }

    fun takePicture() {
        val imageCapture = imageCapture ?: run {
            Log.e("CameraService", "ImageCapture no está configurado correctamente.")
            return
        }

        val outputFile = File(context.externalCacheDir, "captured_image.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(outputFile).build()

        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                Log.d("CameraService", "Imagen guardada en: ${outputFileResults.savedUri}")
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraService", "Error al capturar la imagen: ${exception.message}")
            }
        })
    }
}





