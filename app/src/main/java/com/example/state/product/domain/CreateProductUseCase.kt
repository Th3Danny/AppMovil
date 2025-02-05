package com.example.state.product.domain

import com.example.state.product.data.model.CreateProductRequest
import com.example.state.product.data.model.ProductDTO
import com.example.state.product.data.repository.ProductRepository

class CreateProductUseCase(private val productRepository: ProductRepository) {

    suspend operator fun invoke(productRequest: CreateProductRequest): Result<Unit> {
        return try {
            productRepository.createProduct(productRequest)
        } catch (e: Exception) {
            Result.failure(e)  // En caso de error, retornamos el error
        }
    }
}

