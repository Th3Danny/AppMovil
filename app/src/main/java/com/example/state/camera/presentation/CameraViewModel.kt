package com.example.state.camera.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.state.camera.domain.CameraUseCase
import com.example.state.camera.data.model.CameraCaptureResult
import kotlinx.coroutines.launch
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.core.Preview
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import java.io.File

class CameraViewModel(private val cameraUseCase: CameraUseCase) : ViewModel() {

    private val _captureResult = MutableLiveData<CameraCaptureResult>()
    val captureResult: LiveData<CameraCaptureResult> = _captureResult

    // Método para iniciar la cámara
    fun startCamera(
        lifecycleOwner: LifecycleOwner,
        cameraProvider: ProcessCameraProvider,
        preview: Preview,
        previewView: PreviewView
    ) {
        viewModelScope.launch {
            // Llamamos a startCamera de manera suspendida
            cameraUseCase.startCamera(lifecycleOwner, cameraProvider, preview, previewView)
        }
    }

    // Método para capturar la imagen
    fun captureImage() {
        viewModelScope.launch {
            val result = cameraUseCase.captureImage()  // Llamada sin pasar parámetros
            _captureResult.postValue(result) // Publicamos el resultado
        }
    }
}




