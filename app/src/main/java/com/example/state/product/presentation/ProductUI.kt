package com.example.state.product.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.state.product.data.model.CreateProductRequest
import com.example.state.product.data.model.ProductDTO

@Composable
fun ProductScreen(
    productViewModel: ProductViewModel = viewModel()  // Aquí se utiliza el viewModel
) {

    val products: List<ProductDTO> by productViewModel.products.observeAsState(emptyList())
    val error: String by productViewModel.error.observeAsState("")
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Productos", fontSize = 24.sp)

        // Mostrar la lista de productos
        products.forEach { product ->
            Text(text = "Nombre: ${product.Productonombre} Precio: ${product.Productoprecio}")
        }

        // Mostrar error si lo hay
        if (error.isNotEmpty()) {
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }

        // Campo de texto para agregar nombre
        TextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text("Nombre del Producto") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para agregar el precio
        TextField(
            value = productPrice,
            onValueChange = { productPrice = it },
            label = { Text("Precio del Producto") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para agregar la cantidad
        TextField(
            value = productQuantity,
            onValueChange = { productQuantity = it },
            label = { Text("Cantidad del Producto") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para agregar producto
        Button(
            onClick = {
                // Crear el objeto de producto con los datos ingresados
                val newProduct = CreateProductRequest(productName, productPrice.toDouble(), productQuantity.toInt())
                // Llamar al método de agregar producto en el ViewModel
                productViewModel.addProduct(newProduct)
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            enabled = productName.isNotEmpty() && productPrice.isNotEmpty() && productQuantity.isNotEmpty()
        ) {
            Text("Agregar Producto")
        }
    }
}


