package com.example.state.camera.data.datasource

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraService(private val lifecycleOwner: LifecycleOwner) {

    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraProvider: ProcessCameraProvider

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun startCamera(cameraProvider: ProcessCameraProvider, preview: Preview) {
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        imageCapture = ImageCapture.Builder().build()

        // Vinculamos la cámara al ciclo de vida
        cameraProvider.bindToLifecycle(
            lifecycleOwner, cameraSelector, preview, imageCapture
        )

        this.cameraProvider = cameraProvider // Guardamos la instancia del cameraProvider
    }

    // Capturar la imagen después de que la cámara esté correctamente vinculada
    fun captureImage(outputFileOptions: ImageCapture.OutputFileOptions, callback: ImageCapture.OnImageSavedCallback) {
        // Verificamos que la cámara esté correctamente inicializada antes de capturar
        if (::imageCapture.isInitialized) {
            imageCapture.takePicture(outputFileOptions, executor, callback)
        } else {
            Log.e("CameraService", "La cámara no está correctamente inicializada.")
        }
    }
}


