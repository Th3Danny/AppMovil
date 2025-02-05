package com.example.state.product.data.repository

import android.util.Log
import com.example.state.core.network.RetrofitHelper
import com.example.state.product.data.model.CreateProductRequest
import com.example.state.product.data.model.ProductDTO
import retrofit2.HttpException

class ProductRepository {

    // Aquí obtenemos la instancia del servicio Retrofit.
    private val productService = RetrofitHelper.productService

    // Función para obtener todos los productos
    suspend fun getProducts(): Result<List<ProductDTO>> {
        return try {
            val response = productService.getProducts()

            if (response.isSuccessful) {
                // Si la respuesta es exitosa, retornamos los productos
                Result.success(response.body() ?: emptyList())
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("ProductRepository", "Error: $errorBody")
                Result.failure(Exception("Error al obtener los productos"))
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Excepción: ${e.message}")
            Result.failure(e)
        }
    }

    // Función para agregar un nuevo producto
    suspend fun createProduct(productRequest: CreateProductRequest): Result<ProductDTO> {
        return try {
            // Hacemos la llamada al servicio para crear un producto
            val response = productService.createProduct(productRequest)

            // Si la respuesta es exitosa, devolvemos el producto
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                // Si la respuesta falla, mostramos el error
                val errorBody = response.errorBody()?.string()
                Log.e("ProductRepository", "Error: $errorBody")
                Result.failure(Exception("Error al agregar el producto"))
            }
        } catch (e: Exception) {
            // En caso de excepciones, devolvemos el error
            Log.e("ProductRepository", "Excepción: ${e.message}")
            Result.failure(e)
        }
    }
}

