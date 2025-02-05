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
import com.example.state.product.data.repository.ProductRepository
import com.example.state.product.domain.CreateProductUseCase
import com.example.state.product.domain.GetProductsUseCase
import com.example.state.product.presentation.ProductScreen
import com.example.state.product.presentation.ProductViewModel
import com.example.state.product.presentation.ProductViewModelFactory
import com.example.state.register.data.repository.RegisterRepository
import com.example.state.register.domain.CreateUserUSeCase
import com.example.state.register.presentation.RegisterScreen
import com.example.state.register.presentation.RegisterViewModel
import com.example.state.register.presentation.RegisterViewModelFactory

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    // Crear el LoginUseCase y LoginRepository
    val loginRepository = LoginRepository() // Reemplaza con la instancia real de tu repositorio
    val loginUseCase = LoginUseCase(loginRepository)

    val registerRepository = RegisterRepository()
    val createUserUseCase = CreateUserUSeCase(registerRepository)

    // Crear los casos de uso para productos
    val productRepository = ProductRepository()
    val getProductsUseCase = GetProductsUseCase(productRepository)
    val createProductUseCase = CreateProductUseCase(productRepository)

    NavHost(navController = navController, startDestination = Login::class.java.simpleName) {
        composable(Login::class.java.simpleName) {
            // Usamos ViewModelFactory para obtener una instancia de LoginViewModel
            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(loginUseCase)
            )

            LoginScreen(
                loginViewModel = loginViewModel,
                modifier = Modifier,
                onNavigateToRegister = { navController.navigate(Register::class.java.simpleName) },
                onNavigateToHome = {

                    navController.navigate(Product::class.java.simpleName)
                }
            )
        }

        composable(Register::class.java.simpleName) {
            val registerViewModel: RegisterViewModel = viewModel(
                factory = RegisterViewModelFactory(createUserUseCase, registerRepository)
            )
            RegisterScreen(
                registerViewModel = registerViewModel,
                modifier = Modifier,
                navigateBackToLogin = { navController.navigate(Login::class.java.simpleName) }
            )
        }

        composable(Product::class.java.simpleName) {
            // Usamos el ProductViewModelFactory para obtener una instancia de ProductViewModel
            val productViewModel: ProductViewModel = viewModel(
                factory = ProductViewModelFactory(createProductUseCase, getProductsUseCase)
            )
            ProductScreen(
                productViewModel = productViewModel
            )
        }
    }
}
