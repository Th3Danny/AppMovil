package com.example.state.product.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.state.product.data.model.CreateProductRequest
import com.example.state.product.data.model.ProductDTO


@Composable
fun ProductScreen(
    productViewModel: ProductViewModel = viewModel(),
    navigateToCamera: () -> Unit
) {
    val products: List<ProductDTO> by productViewModel.products.observeAsState(emptyList())
    val error: String by productViewModel.error.observeAsState("")

    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }
    var showForm by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Productos", fontSize = 24.sp)

        // Si showForm es verdadero, mostramos la tarjeta con el formulario
        if (showForm) {
            ProductCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = productName,
                        onValueChange = { productName = it },
                        label = { Text("Nombre del Producto") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = productPrice,
                        onValueChange = { productPrice = it },
                        label = { Text("Precio del Producto") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = productQuantity,
                        onValueChange = { productQuantity = it },
                        label = { Text("Cantidad del Producto") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {

                            val newProduct = CreateProductRequest(
                                productName,
                                productPrice.toDouble(),
                                productQuantity.toInt()
                            )
                            productViewModel.addProduct(newProduct)

                            // Limpiar los campos después de guardar
                            productName = ""
                            productPrice = ""
                            productQuantity = ""

                            // Ocultamos el formulario después de agregar el producto
                            showForm = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = productName.isNotEmpty() && productPrice.isNotEmpty() && productQuantity.isNotEmpty()
                    ) {
                        Text("Guardar Producto")
                    }
                }
            }
        }


        // Botón para mostrar el formulario de agregar producto
        Button(
            onClick = { showForm = !showForm },
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Text("Agregar Producto")
        }

        // Mostrar la lista de productos usando LazyColumn
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 10.dp)
        ) {
            items(products) { product ->  // Iteramos sobre la lista de productos
                ProductCard {  // Usamos ProductCard para cada producto
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(text = product.Productonombre, fontWeight = FontWeight.Bold)
                        Text(text = "Precio: \$${product.Productoprecio}")
                    }
                }
            }
        }

        // Botón para navegar a la pantalla de la cámara
        Button(
            onClick = { navigateToCamera() },
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Text("Abrir Cámara")
        }

        // Mostrar error si lo hay
        if (error.isNotEmpty()) {
            Text(text = error, color = Color.Red)
        }


    }
}



