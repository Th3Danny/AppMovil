package com.example.state.login.data.model

data class LoginResponse(
    val token: String,
    val usuario: Usuario
)

data class Usuario(
    val nombre: String
)