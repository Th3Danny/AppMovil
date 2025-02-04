package com.example.state.core.network

import com.example.state.register.data.datasource.RegisterService
import com.example.state.login.data.datasource.LoginService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val BASE_URL = "http://10.1.0.101:3000/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val registerService: RegisterService = retrofit.create(RegisterService::class.java)
    val loginService: LoginService = retrofit.create(LoginService::class.java)


}