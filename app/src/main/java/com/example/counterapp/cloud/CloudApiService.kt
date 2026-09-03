package com.example.counterapp.cloud

import retrofit2.http.GET

interface CloudApiService {
    @GET("hello")
    suspend fun getHello(): String
}
