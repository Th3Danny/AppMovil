package com.example.state.camera.data.datasource

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log


import androidx.core.content.FileProvider


import java.io.File
import java.io.IOException

class CameraService(private val context: Context) {

    fun getPhotoUri(context: Context): Pair<Uri, String>? {
        return try {
            // 📌 Obtiene la ruta correcta de almacenamiento permitida
            val picturesDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MyApp")

            if (!picturesDir.exists()) picturesDir.mkdirs() // Crea la carpeta si no existe

            val photoFile = File(picturesDir, "IMG_${System.currentTimeMillis()}.jpg")

            // 📌 Genera la URI con FileProvider
            val photoUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider", // 📌 Asegúrate de que coincide con el Manifest
                photoFile
            )

            Log.d("CameraService", "✅ Archivo creado en: ${photoFile.absolutePath}")
            Pair(photoUri, photoFile.absolutePath) // 📌 Retorna la URI y la ruta absoluta
        } catch (e: Exception) {
            Log.e("CameraService", "❌ Error al generar URI: ${e.message}")
            null
        }
    }



    fun takePicture(): Uri? {
        return try {
            val tempFile = File.createTempFile("IMG_", ".jpg", context.cacheDir)
            val savedFile = saveToLocalFolder(tempFile)

            if (savedFile != null) {
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    savedFile
                )
                Log.d("CameraService", "✅ Imagen guardada y URI generada: $uri")
                uri
            } else {
                Log.e("CameraService", "❌ No se pudo guardar la imagen.")
                null
            }
        } catch (e: Exception) {
            Log.e("CameraService", "❌ Error al capturar la imagen: ${e.message}")
            null
        }
    }



    private fun saveToGallery(context: Context, imageFile: File): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, imageFile.name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp") // Se guarda en "Galería > Pictures > MyApp"
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            resolver.openOutputStream(uri)?.use { outputStream ->
                imageFile.inputStream().copyTo(outputStream)
            }
            Log.d("CameraService", "✅ Imagen guardada en la galería: $uri")
        } else {
            Log.e("CameraService", "❌ Error al guardar en la galería")
        }

        return uri
    }

    private fun saveToLocalFolder(imageFile: File): File? {
        return try {
            val localFolder = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MyAppImages")
            if (!localFolder.exists()) {
                localFolder.mkdirs() // Crear la carpeta si no existe
            }

            val localFile = File(localFolder, imageFile.name)
            imageFile.copyTo(localFile, overwrite = true)
            Log.d("CameraService", "✅ Imagen guardada en carpeta local: ${localFile.absolutePath}")
            localFile
        } catch (e: Exception) {
            Log.e("CameraService", "❌ Error al guardar en carpeta local: ${e.message}")
            null
        }
    }




}





