package com.example.state.product.data.datasource

import com.example.state.product.data.model.CreateProductRequest
import com.example.state.product.data.model.ProductDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductService {

    // Obtener productos
    @GET("productos")
    suspend fun getProducts(): Response<List<ProductDTO>>


    // Crear producto
    @POST("productos")
    suspend fun createProduct(@Body product: CreateProductRequest): Response<ProductDTO>
}
