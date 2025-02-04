package com.example.state.login.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel, // Asegúrate de que este parámetro esté definido
    modifier: Modifier = Modifier,  // Asegúrate de que este parámetro también esté definido
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val email: String by loginViewModel.username.observeAsState("")
    val password: String by loginViewModel.password.observeAsState("")
    val error: String by loginViewModel.error.observeAsState("")
    val success: Boolean by loginViewModel.success.observeAsState(false)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "Login",
            fontSize = 32.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Campo de texto para email
        TextField(
            value = email,
            onValueChange = { loginViewModel.onChangeUsername(it) },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo de texto para contraseña
        TextField(
            value = password,
            onValueChange = { loginViewModel.onChangePassword(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botón para iniciar sesión
        Button(
            onClick = { loginViewModel.onLogin() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Mensaje de error
        if (error.isNotEmpty()) {
            Text(text = error, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botón para navegar a la vista de registro
        Button(
            onClick = { onNavigateToRegister() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            )
        ) {
            Text("Registrarte")
        }
    }
}
