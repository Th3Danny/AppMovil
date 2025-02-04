package com.example.state.login.domain

import com.example.state.login.data.model.LoginRequest
import com.example.state.login.data.model.LoginResponse
import com.example.state.login.data.repository.LoginRepository

class LoginUseCase(private val repository: LoginRepository) {
    suspend operator fun invoke(request: LoginRequest): Result<LoginResponse> {
        return try {
            repository.loginUser(request)
        } catch (e: Exception) {
            // Manejo de excepciones
            Result.failure(e)
        }
    }
}
