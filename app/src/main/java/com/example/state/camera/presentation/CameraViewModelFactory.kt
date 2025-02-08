package com.example.state.camera.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.state.camera.domain.CameraUseCase

class CameraViewModelFactory(
    private val cameraUseCase: CameraUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CameraViewModel(cameraUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
