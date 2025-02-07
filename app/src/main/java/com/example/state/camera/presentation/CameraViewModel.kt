package com.example.state.camera.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.state.camera.domain.CameraUseCase
import com.example.state.camera.data.model.CameraCaptureResult
import kotlinx.coroutines.launch
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.core.Preview

class CameraViewModel(private val cameraUseCase: CameraUseCase) : ViewModel() {

    private val _captureResult = MutableLiveData<CameraCaptureResult>()
    val captureResult: LiveData<CameraCaptureResult> get() = _captureResult

    // Método para iniciar la cámara
    fun startCamera(cameraProvider: ProcessCameraProvider, preview: Preview) {
        viewModelScope.launch {
            cameraUseCase.startCamera(cameraProvider, preview)
        }
    }

    // Método para capturar una imagen
    fun captureImage() {
        viewModelScope.launch {
            val result = cameraUseCase.captureImage()
            _captureResult.value = result
        }
    }
}


