package com.example.state.product.domain

import com.example.state.product.data.model.CreateProductRequest
import com.example.state.product.data.model.ProductDTO
import com.example.state.product.data.repository.ProductRepository

class CreateProductUseCase(private val productRepository: ProductRepository) {

    // Crear un nuevo producto
    suspend operator fun invoke(productRequest: CreateProductRequest): Result<ProductDTO> {
        return try {
            productRepository.createProduct(productRequest)  // Llamamos al repositorio para crear el producto
        } catch (e: Exception) {
            Result.failure(e)  // En caso de error, retornamos el error
        }
    }
}
