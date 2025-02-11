package com.example.state

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.state.core.navigation.NavigationWrapper


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar permisos al inicio
        checkPermissions()

        setContent {
            NavigationWrapper()
        }
    }

    private fun checkPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        // Verificar permisos de cámara
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.CAMERA)
        }

        // Verificar permisos de lectura de imágenes (Android 14+)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.READ_MEDIA_IMAGES)
        }

        // Solicitar permisos si es necesario
        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionsLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            Toast.makeText(this, "Todos los permisos concedidos", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.CAMERA] == true &&
                permissions[Manifest.permission.READ_MEDIA_IMAGES] == true
            ) {
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permisos denegados. Actívalos en Configuración.", Toast.LENGTH_LONG).show()
            }
        }

    // Manejo del ciclo de vida para debug
    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart llamado")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume llamado")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause llamado")
        // Libera recursos si es necesario
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop llamado")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy llamado")
    }
}
