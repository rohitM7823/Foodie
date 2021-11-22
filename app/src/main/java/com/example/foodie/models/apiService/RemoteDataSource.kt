package com.example.foodie.models.apiService

import com.example.foodie.models.source.Recipes
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val connection: Connection) {

    suspend fun getRecipes(queries: Map<String, String>): Response<Recipes> {
        return connection.getData(queries)
    }

}