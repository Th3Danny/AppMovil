package com.example.state.register.presentation


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.example.state.register.data.model.CreateUserRequest
import kotlinx.coroutines.launch

//@Preview(showBackground = true)
@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel,
    modifier: Modifier,
    navigateBackToLogin: () -> Unit
) {
    val nombre: String by registerViewModel.nombre.observeAsState("")
    val correo: String by registerViewModel.correo.observeAsState("") // Es necesario cambiar el nombre del campo 'correo' en el ViewModel
    val password: String by registerViewModel.password.observeAsState("")
    val success: Boolean by registerViewModel.success.observeAsState(false)
    val error: String by registerViewModel.error.observeAsState("")
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Create Account",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(30.dp))

        // Campo para nombre
        TextField(
            value = nombre,
            onValueChange = { registerViewModel.onChangeUsername(it) },
            label = { Text("Nombre") },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text("Juan Pérez") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Person Icon") },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        Spacer(Modifier.height(10.dp))

        // Campo para correo
        TextField(
            value = correo,
            onValueChange = { registerViewModel.onChangeCorreo(it) }, // Asegúrate de tener un método onChangeCorreo en tu ViewModel
            label = { Text("Correo") },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text("juan@example.com") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Correo Icon") },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        Spacer(Modifier.height(10.dp))

        // Mostrar error
        Text(text = error)

        // Campo para contraseña
        TextField(
            value = password,
            onValueChange = { registerViewModel.onChangePassword(it) },
            label = { Text("Password") },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Lock Icon") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))


        // Botón de registro
        Button(
            onClick = {
                // Crear el objeto de usuario con los datos ingresados
                val user = CreateUserRequest(nombre, correo, password)
                // Llamada al método onClick para registrar al usuario
                registerViewModel.viewModelScope.launch {
                    registerViewModel.onClick(user)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .height(50.dp),
            enabled = nombre.isNotEmpty() && correo.isNotEmpty() && password.isNotEmpty(), // Botón habilitado solo si los campos no están vacíos
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Sign up",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}