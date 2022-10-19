package com.javiermendez.tp2_usuarios

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RandomUserApiService {
    @GET("/api")
    fun getUsers(@Query("results") limit: String): Call<RandomUserResponse>
}