package com.example.foodie.models.apiService

import com.example.foodie.models.source.Recipes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Connection {
    @GET("/recipes/complexSearch")
    suspend fun getData(
        @QueryMap queries: Map<String, String>
    ): Response<Recipes>
}