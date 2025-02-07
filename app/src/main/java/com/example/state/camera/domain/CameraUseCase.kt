package com.example.state.camera.domain

import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import com.example.state.camera.data.model.CameraCaptureResult
import com.example.state.camera.data.repository.CameraRepository

class CameraUseCase(private val cameraRepository: CameraRepository) {

    suspend fun startCamera(cameraProvider: ProcessCameraProvider, preview: Preview) {
        cameraRepository.startCamera(cameraProvider, preview)
    }

    suspend fun captureImage(): CameraCaptureResult {
        return cameraRepository.captureImage()
    }
}

