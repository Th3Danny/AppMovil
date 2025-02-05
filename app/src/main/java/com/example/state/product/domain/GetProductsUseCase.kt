package com.example.state.product.domain

import com.example.state.product.data.model.ProductDTO
import com.example.state.product.data.repository.ProductRepository

class GetProductsUseCase(private val productRepository: ProductRepository) {

    // Obtener todos los productos
    suspend operator fun invoke(): Result<List<ProductDTO>> {
        return try {
            productRepository.getProducts()  // Llamamos al repositorio para obtener los productos
        } catch (e: Exception) {
            Result.failure(e)  // En caso de error, retornamos el error
        }
    }
}
