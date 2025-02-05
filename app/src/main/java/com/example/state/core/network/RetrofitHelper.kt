package com.example.state.core.network

import com.example.state.register.data.datasource.RegisterService
import com.example.state.login.data.datasource.LoginService
import com.example.state.product.data.datasource.ProductService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val BASE_URL = "http://192.168.3.17:3002/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val registerService: RegisterService = retrofit.create(RegisterService::class.java)
    val loginService: LoginService = retrofit.create(LoginService::class.java)
    val productService: ProductService = retrofit.create(ProductService::class.java)


}