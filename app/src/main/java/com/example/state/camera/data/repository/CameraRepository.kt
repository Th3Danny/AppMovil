package com.example.state.camera.data.repository

import android.util.Log
import androidx.camera.core.ImageCapture
import com.example.state.camera.data.datasource.CameraService
import com.example.state.camera.data.model.CameraCaptureResult
import android.content.Context
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner

class CameraRepository(private val cameraService: CameraService, private val context: Context) {

    // Método para capturar la imagen
    fun captureImage(): CameraCaptureResult {
        var result = CameraCaptureResult("", false)

        try {
            // Captura la imagen usando CameraService
            cameraService.takePicture()  // No pasamos argumentos aquí
            result = CameraCaptureResult("Image saved", true)
        } catch (e: Exception) {
            result = CameraCaptureResult("", false)
            Log.e("CameraRepository", "Error en la captura de la imagen: ${e.message}")
        }

        return result
    }

    // Método para iniciar la cámara
    fun startCamera(
        lifecycleOwner: LifecycleOwner,
        cameraProvider: ProcessCameraProvider,
        preview: Preview,
        previewView: PreviewView
    ) {
        try {
            // Desvincula todos los casos de uso anteriores
            cameraProvider.unbindAll()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            // Vincula los casos de uso al ciclo de vida del propietario
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                cameraService.getImageCapture() // Asegúrate de que ImageCapture esté correctamente vinculado
            )

            preview.setSurfaceProvider(previewView.surfaceProvider) // Asignar SurfaceProvider para la vista previa
            Log.d("CameraRepository", "Cámara vinculada exitosamente")
        } catch (e: Exception) {
            Log.e("CameraRepository", "Error al iniciar la cámara: ${e.message}")
        }
    }
}


