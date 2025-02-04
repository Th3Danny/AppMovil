package com.example.state.register.domain

import com.example.state.register.data.model.CreateUserRequest
import com.example.state.register.data.model.UserDTO
import com.example.state.register.data.model.UsernameValidateDTO
import com.example.state.register.data.repository.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateUserUSeCase(private val registerRepository: RegisterRepository) {

    // Esta función recibe un CreateUserRequest y devuelve el resultado de registrar al usuario
    suspend operator fun invoke(user: CreateUserRequest) = withContext(Dispatchers.IO) {
        try {
            val response = registerRepository.registerUser(user)
            response
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}