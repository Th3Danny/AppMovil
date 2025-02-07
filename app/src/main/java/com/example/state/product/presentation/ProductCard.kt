package com.example.state.product.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 5.dp,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    // Aplicamos un sombreado con un Box para simular el efecto de elevación
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .shadow(elevation = elevation, shape = shape)
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = shape
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                content()  // El contenido dentro de la Card
            }
        }
    }
}
