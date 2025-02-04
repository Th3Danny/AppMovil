package com.example.state.register.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.state.register.data.model.CreateUserRequest
import com.example.state.register.data.model.UserDTO
import com.example.state.register.data.model.UsernameValidateDTO
import com.example.state.register.domain.CreateUserUSeCase
import com.example.state.register.domain.UsernameValidateUseCase
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val usernameUseCase = UsernameValidateUseCase()
    private val createUseCase = CreateUserUSeCase()

    private var _username = MutableLiveData<String>("")
    val username: LiveData<String> = _username

    private var _password = MutableLiveData<String>("")
    val password: LiveData<String> = _password

    private var _success = MutableLiveData<Boolean>(false)
    val success: LiveData<Boolean> = _success

    private var _error = MutableLiveData<String>("")
    val error: LiveData<String> = _error

    fun onChangeUsername(username: String) {
        _username.value = username
    }

    fun onChangePassword(password: String) {
        _password.value = password
    }

    // Llamada para validar el username
    fun onFocusChanged() {
        viewModelScope.launch {
            try {
                val result = usernameUseCase()
                result.onSuccess { data ->
                    if (data.success) {
                        _success.value = true
                        _error.value = ""
                    } else {
                        _error.value = "El username ya existe"
                    }
                }.onFailure { exception ->
                    _error.value = exception.message ?: "Error desconocido"
                }
            } catch (exception: Exception) {
                _error.value = exception.message ?: "Error de validación"
            }
        }
    }

    // Llamada para registrar al usuario
    fun onClick(user: CreateUserRequest) {
        viewModelScope.launch {
            try {
                val result = createUseCase(user)
                result.onSuccess {
                    // Aquí se puede manejar el éxito, por ejemplo, navegando o mostrando un mensaje
                    _success.value = true
                    _error.value = ""
                }.onFailure { exception ->
                    _error.value = exception.message ?: "Error al registrar usuario"
                }
            } catch (exception: Exception) {
                _error.value = exception.message ?: "Error de registro"
            }
        }
    }
}
