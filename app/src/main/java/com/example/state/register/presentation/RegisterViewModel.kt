package com.example.state.register.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.state.register.data.model.CreateUserRequest
import com.example.state.register.data.repository.RegisterRepository
import com.example.state.register.domain.CreateUserUSeCase
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val createUserUseCase: CreateUserUSeCase,  // Aquí es donde se necesita usar el caso de uso
    private val registerRepository: RegisterRepository // El repositorio sigue siendo útil para validaciones
) : ViewModel() {

    private var _nombre = MutableLiveData<String>("")
    val nombre: LiveData<String> = _nombre

    private var _correo = MutableLiveData<String>("")
    val correo: LiveData<String> = _correo

    private var _password = MutableLiveData<String>("")
    val password: LiveData<String> = _password

    private var _success = MutableLiveData<Boolean>(false)
    val success: LiveData<Boolean> = _success

    private var _error = MutableLiveData<String>("")
    val error: LiveData<String> = _error

    fun onChangeUsername(nombre: String) {
        _nombre.value = nombre
    }

    fun onChangeCorreo(correo: String) {
        _correo.value = correo
    }

    fun onChangePassword(password: String) {
        _password.value = password
    }

    // Función para validar el nombre de usuario
    fun onFocusChanged() {
        viewModelScope.launch {
            try {
                val result = registerRepository.validateUsername()
                result.onSuccess { data ->
                    if (data.success) {
                        _success.value = true
                        _error.value = ""
                    } else {
                        _error.value = data.message // Si el nombre de usuario ya existe
                    }
                }.onFailure { exception ->
                    _error.value = exception.message ?: "Error de validación"
                }
            } catch (exception: Exception) {
                _error.value = exception.message ?: "Error de validación"
            }
        }
    }

    // Llamada para registrar al usuario usando el caso de uso
    fun onClick(user: CreateUserRequest) {
        viewModelScope.launch {
            try {
                val result = registerRepository.registerUser(user)
                result.onSuccess {
                    _success.value = true
                    _error.value = ""
                }.onFailure { exception ->
                    _success.value = false
                    _error.value = exception.message ?: "Error desconocido al registrar usuario"
                    Log.e("RegisterViewModel", "Error al registrar usuario: ${exception.message}")
                }
            } catch (e: Exception) {
                _success.value = false
                _error.value = "Error de red: ${e.message}"
                Log.e("RegisterViewModel", "Error de red: ${e.message}")
            }
        }
    }

}
