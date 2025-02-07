package com.example.state.register.data.datasource

import com.example.state.register.data.model.CreateUserRequest
import com.example.state.register.data.model.UserDTO
import com.example.state.register.data.model.UsernameValidateDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegisterService {

    @GET("api/auth/validateUsername")
    suspend fun validateUsername() : Response<UsernameValidateDTO>

    @POST("auth/register")
    suspend fun createUser(@Body request : CreateUserRequest) : Response<UserDTO>

}


