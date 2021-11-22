package com.example.foodie.models.source


import com.google.gson.annotations.SerializedName

data class Us(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("unitLong")
    val unitLong: String,
    @SerializedName("unitShort")
    val unitShort: String
)