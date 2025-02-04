package com.example.state.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.state.login.presentation.LoginScreen
import com.example.state.login.presentation.LoginViewModel
import com.example.state.login.presentation.LoginViewModelFactory
import com.example.state.login.domain.LoginUseCase
import com.example.state.login.data.repository.LoginRepository

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    // Crear el LoginUseCase y LoginRepository
    val loginRepository = LoginRepository() // Reemplaza con la instancia real de tu repositorio
    val loginUseCase = LoginUseCase(loginRepository)

    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") {
            // Usamos ViewModelFactory para obtener una instancia de LoginViewModel
            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(loginUseCase)
            )

            LoginScreen(
                loginViewModel = loginViewModel,
                modifier = Modifier,
                onNavigateToRegister = { navController.navigate("Register") },
                onNavigateToHome = { navController.navigate("Home") }
            )
        }
        composable("Register") {
            // Aquí va el código para la pantalla de registro
        }
    }
}
