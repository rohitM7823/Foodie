package com.example.foodie.models.source


import com.google.gson.annotations.SerializedName

data class Recipes(
    @SerializedName("number")
    val number: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("totalResults")
    val totalResults: Int
)