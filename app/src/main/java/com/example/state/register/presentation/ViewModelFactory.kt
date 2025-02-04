package com.example.state.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.state.register.data.repository.RegisterRepository
import com.example.state.register.domain.CreateUserUSeCase

class RegisterViewModelFactory(
    private val createUserUseCase: CreateUserUSeCase,
    private val registerRepository: RegisterRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(createUserUseCase, registerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}