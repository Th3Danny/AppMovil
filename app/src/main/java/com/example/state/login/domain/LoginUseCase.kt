package com.example.state.login.domain

import com.example.state.login.data.model.LoginRequest
import com.example.state.login.data.model.LoginResponse
import com.example.state.login.data.repository.LoginRepository

class LoginUseCase(private val repository: LoginRepository) {
    suspend operator fun invoke(request: LoginRequest): Result<LoginResponse> {
        return try {
            // Hacer la llamada al repositorio, que a su vez hace la petición a la API
            repository.loginUser(request)
        } catch (e: Exception) {
            // Manejo de excepciones
            Result.failure(e)
        }
    }
}
