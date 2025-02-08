package com.example.state.camera.domain

import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import com.example.state.camera.data.repository.CameraRepository
import com.example.state.camera.data.model.CameraCaptureResult

import androidx.lifecycle.LifecycleOwner

class CameraUseCase(private val cameraRepository: CameraRepository) {

    // Método para iniciar la cámara
    suspend fun startCamera(
        lifecycleOwner: LifecycleOwner,
        cameraProvider: ProcessCameraProvider,
        preview: Preview,
        previewView: PreviewView
    ) {
        cameraRepository.startCamera(lifecycleOwner, cameraProvider, preview, previewView)
    }

    // Método para capturar la imagen
    suspend fun captureImage(): CameraCaptureResult {
        return cameraRepository.captureImage()  // Llamamos sin pasar parámetros
    }
}


