package com.example.state.login.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.state.login.domain.LoginUseCase
import com.example.state.login.data.model.LoginRequest
import com.example.state.login.data.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private var _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private var _success = MutableLiveData<Boolean>(false)
    val success: LiveData<Boolean> = _success

    private var _error = MutableLiveData<String>("")
    val error: LiveData<String> = _error

    private var _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    fun onLogin() {
        viewModelScope.launch {
            // Log para debugging
            Log.d("LoginViewModel", "onLogin iniciado con usuario: ${_username.value}")
            try {
                // Creamos el objeto LoginRequest
                val loginRequest = LoginRequest(
                    correo = _username.value ?: "",
                    password = _password.value ?: ""
                )

                // Llamada al use case (que a su vez llama al repositorio)
                val result = loginUseCase(loginRequest)
                result.onSuccess { loginResponse ->
                    Log.d("LoginViewModel", "Login exitoso: Token recibido")
                    _success.value = true
                    _error.value = ""
                    _token.value = loginResponse.token // Guardamos el token si el login fue exitoso
                }.onFailure { exception ->
                    Log.e("LoginViewModel", "Login fallido: ${exception.message}")
                    _success.value = false
                    _error.value = exception.message ?: "Error desconocido"
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Excepción: ${e.message}")
                _success.value = false
                _error.value = e.message ?: "Error al intentar realizar la operación"
            }
        }
    }

    fun onChangeUsername(username: String) {
        _username.value = username
    }

    fun onChangePassword(password: String) {
        _password.value = password
    }


}
