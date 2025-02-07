package com.example.state.camera.presentation

import android.app.Activity
import android.content.pm.PackageManager
import android.Manifest
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.camera.view.PreviewView
import com.example.state.camera.data.model.CameraCaptureResult
import kotlinx.coroutines.launch
import androidx.camera.core.ImageCapture
import androidx.compose.ui.viewinterop.AndroidView

private const val CAMERA_REQUEST_CODE = 1001

@Composable
fun CameraScreen(cameraViewModel: CameraViewModel) {
    var captureResult by remember { mutableStateOf<CameraCaptureResult?>(null) }
    var isCapturing by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraProvider = remember { ProcessCameraProvider.getInstance(context) }
    val preview = remember { Preview.Builder().build() }
    val imageCapture = remember { ImageCapture.Builder().build() }

    // Verificamos si tenemos permiso para usar la cámara
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // Si no tenemos permisos, los solicitamos
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    LaunchedEffect(Unit) {
        val cameraProviderInstance = cameraProvider.get()

        // Vinculamos la cámara a un LifecycleOwner
        cameraProviderInstance.bindToLifecycle(
            lifecycleOwner,
            CameraSelector.DEFAULT_BACK_CAMERA,
            preview,
            imageCapture // Aseguramos que ImageCapture está vinculado también
        )
    }

    // Vista previa de la cámara dentro de un Composable
    val previewView = remember { PreviewView(context) }

    // Vinculamos el SurfaceProvider del Preview a la vista previa
    preview.setSurfaceProvider(previewView.surfaceProvider)

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                // Lanza la captura de imagen
                isCapturing = true
                coroutineScope.launch {
                    cameraViewModel.captureImage()
                    isCapturing = false
                }
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
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )
    }
}
