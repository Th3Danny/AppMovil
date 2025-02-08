package com.example.state.camera.presentation

import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.camera.view.PreviewView
import androidx.compose.ui.viewinterop.AndroidView
import com.example.state.camera.data.datasource.CameraService
import com.example.state.camera.data.model.CameraCaptureResult
import java.io.File
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraScreen(cameraViewModel: CameraViewModel = viewModel()) {
    var captureResult by remember { mutableStateOf<CameraCaptureResult?>(null) }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProvider = remember { ProcessCameraProvider.getInstance(context) }
    val preview = remember { Preview.Builder().build() }
    val previewView = remember { PreviewView(context) }

    // Configurar la cámara
    LaunchedEffect(Unit) {
        val cameraProviderInstance = cameraProvider.get()
        cameraProviderInstance.unbindAll()

        // Configurar la cámara
        cameraViewModel.startCamera(lifecycleOwner, cameraProviderInstance, preview, previewView)

        preview.setSurfaceProvider(previewView.surfaceProvider) // Asignar el SurfaceProvider para la vista previa
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                cameraViewModel.captureImage()  // Llamamos a captureImage después de que la cámara esté configurada
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = "Capturar Imagen")
        }

        captureResult?.let {
            if (it.success) {
                Text("Imagen guardada en: ${it.imageUri}")
            } else {
                Text("Error al capturar la imagen", color = MaterialTheme.colorScheme.error)
            }
        }

        // Vista previa de la cámara
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
    }
}


