package com.example.state.camera.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.state.camera.domain.CameraUseCase
import com.example.state.camera.data.model.CameraCaptureResult
import java.io.File

class CameraViewModel(private val cameraUseCase: CameraUseCase) : ViewModel() {

    private val _captureResult = MutableLiveData<CameraCaptureResult>()
    val captureResult: LiveData<CameraCaptureResult> = _captureResult

    // Propiedad para almacenar la URI de la foto
    private var photoUri: Uri? = null

    private var photoFilePath: String? = null // Ruta absoluta del archivo

    fun getCameraIntent(context: Context): Intent? {
        return try {
            val photoData = cameraUseCase.getPhotoUri(context) // ✅ Se pasa el contexto correctamente

            if (photoData != null) {
                val (uri, filePath) = photoData // Ahora la desestructuración funcionará
                photoFilePath = filePath // Guardamos la ruta absoluta para acceder después

                Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                    putExtra(MediaStore.EXTRA_OUTPUT, uri)
                    addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
            } else {
                Log.e("CameraViewModel", "❌ No se pudo obtener URI para la imagen.")
                null
            }
        } catch (e: Exception) {
            Log.e("CameraViewModel", "Error al obtener intent de cámara: ${e.message}")
            null
        }
    }



    fun handleCameraResult(resultCode: Int, context: Context) {
        if (resultCode == Activity.RESULT_OK && photoFilePath != null) {
            val file = File(photoFilePath!!)
            if (file.exists()) {
                Log.d("CameraViewModel", "✅ Foto capturada correctamente en: ${file.absolutePath}")
                _captureResult.postValue(CameraCaptureResult(file.absolutePath, true))
            } else {
                // 🔥 CORREGIR la ruta antes de marcar error
                val correctedFile = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MyApp/${File(photoFilePath!!).name}")

                if (correctedFile.exists()) {
                    Log.d("CameraViewModel", "✅ Imagen encontrada en: ${correctedFile.absolutePath}")
                    _captureResult.postValue(CameraCaptureResult(correctedFile.absolutePath, true))
                } else {
                    Log.e("CameraViewModel", "❌ Archivo de imagen no encontrado en: ${correctedFile.absolutePath}")
                    _captureResult.postValue(CameraCaptureResult("", false))
                }
            }
        } else {
            Log.e("CameraViewModel", "❌ Error al manejar el resultado de la cámara.")
            _captureResult.postValue(CameraCaptureResult("", false))
        }
    }



}





