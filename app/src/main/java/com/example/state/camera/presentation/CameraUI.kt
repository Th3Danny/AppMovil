package com.example.state.camera.presentation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import java.io.File


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun CameraScreen(cameraViewModel: CameraViewModel) {
    val context = LocalContext.current
    val activity = context as? Activity

    val captureResult by cameraViewModel.captureResult.observeAsState()

    // Estado para verificar permisos
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasPermission = permissions[Manifest.permission.CAMERA] == true &&
                permissions[Manifest.permission.READ_MEDIA_IMAGES] == true
    }

    // Solicitar permisos si no están concedidos
    if (!hasPermission) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES
            )
        )
    }

    // Registrar el ActivityResultLauncher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        cameraViewModel.handleCameraResult(result.resultCode) // Llamar a handleCameraResult
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (hasPermission) {
            // Botón para abrir la cámara
            Button(onClick = {
                val intent = cameraViewModel.getCameraIntent(context)
                if (intent != null) {
                    cameraLauncher.launch(intent) // Lanzar el Intent de la cámara
                } else {
                    Log.e("CameraScreen", "❌ No se pudo generar el Intent de cámara.")
                }
            }) {
                Text(text = "Capturar Imagen")
            }

            captureResult?.let { result ->
                if (result.success) {
                    val imageFile = File(result.imageUri) // Usamos la ruta absoluta directamente
                    if (imageFile.exists()) {
                        val imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)?.asImageBitmap()
                        if (imageBitmap != null) {
                            Image(
                                bitmap = imageBitmap,
                                contentDescription = "Imagen capturada",
                                modifier = Modifier.size(200.dp)
                            )
                        } else {
                            Log.e("CameraScreen", "❌ No se pudo cargar el bitmap de la imagen.")
                            Text("Error al cargar la imagen", color = MaterialTheme.colorScheme.error)
                        }
                    } else {
                        Log.e("CameraScreen", "❌ Archivo de imagen no encontrado en: ${imageFile.absolutePath}")
                        Text("Archivo no encontrado", color = MaterialTheme.colorScheme.error)
                    }
                } else {
                    Text(
                        text = "Error al capturar la imagen",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }



        } else {
            Text(
                text = "Se necesita permiso para usar la cámara",
                color = MaterialTheme.colorScheme.error
            )
            Button(onClick = {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_MEDIA_IMAGES
                    )
                )
            }) {
                Text("Solicitar Permisos")
            }
        }
    }
}
