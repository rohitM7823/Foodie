package com.example.foodie.models.utill

sealed class ConnectionResult<T>(
    var data: T? = null,
    var message: String? = null
) {

    class Success<T>(data: T) : ConnectionResult<T>(data)
    class Failure<T>(data: T? = null, message: String?) : ConnectionResult<T>(data, message)
    class Loading<T>: ConnectionResult<T>()

}