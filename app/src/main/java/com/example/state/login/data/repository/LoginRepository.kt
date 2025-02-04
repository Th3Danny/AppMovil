package com.example.state.login.data.repository

import com.example.state.core.network.RetrofitHelper
import com.example.state.login.data.model.LoginRequest
import com.example.state.login.data.model.LoginResponse

class LoginRepository {
    private val loginService = RetrofitHelper.loginService

    suspend fun loginUser(request: LoginRequest) : Result<LoginResponse> {
        return try {
            val response = loginService.loginUser(request)

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error desconocido"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
