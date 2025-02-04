package com.example.state.register.data.repository

import android.util.Log
import com.example.state.core.network.RetrofitHelper
import com.example.state.register.data.model.CreateUserRequest
import com.example.state.register.data.model.UserDTO
import com.example.state.register.data.model.UsernameValidateDTO

class RegisterRepository {
    private val registerService = RetrofitHelper.registerService

    suspend fun registerUser(user: CreateUserRequest): Result<UserDTO> {
        return try {
            val response = registerService.createUser(user)

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("RegisterRepository", "Error: $errorBody")
                Result.failure(Exception("Error al registrar usuario"))
            }
        } catch (e: Exception) {
            Log.e("RegisterRepository", "Excepción: ${e.message}")
            Result.failure(e)
        }
    }


    // Función para validar el nombre de usuario
    suspend fun validateUsername(): Result<UsernameValidateDTO> {
        return try {
            val response = registerService.validateUsername()

            if (response.isSuccessful) {
                // Retornamos el resultado exitoso con el cuerpo de la respuesta
                Result.success(response.body()!!)
            } else {
                // Si la respuesta no es exitosa, retornamos un error
                Result.failure(Exception("Error al validar el nombre de usuario"))
            }
        } catch (e: Exception) {
            // En caso de error, retornamos una excepción
            Result.failure(e)
        }
    }
}