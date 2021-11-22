package com.example.foodie.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodie.models.Repository
import com.example.foodie.models.source.Recipes
import com.example.foodie.models.utill.ConnectionResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    var recipesResponse: MutableLiveData<ConnectionResult<Recipes>> = MutableLiveData()


    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        recipesSafeCall(queries)
    }

    private suspend fun recipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = ConnectionResult.Loading()
        if (isConnectionAvaliable()) {
            try {
                val response = repository.rDataSource.getRecipes(queries)
                recipesResponse.value = handleResponse(response)
            } catch (ex: Exception) {
                recipesResponse.value = ConnectionResult.Failure(null, ex.message)
            }

        } else {
            recipesResponse.value = ConnectionResult.Failure(null, "Has No Internet Connection")
        }
    }

    private fun handleResponse(response: Response<Recipes>): ConnectionResult<Recipes> {
        when {
            response.message().toString().contains("timeout") -> {
                return ConnectionResult.Failure(null, "Connection Timeout")
            }
            response.code() == 402 -> {
                return ConnectionResult.Failure(null, "APIKEY Has Limitation")
            }

            response.body()!!.totalResults == 0 -> {
                return ConnectionResult.Failure(null, "Recipes Not Found!!")
            }

            response.isSuccessful -> {
                return ConnectionResult.Success(response.body()!!)
            }
            else -> {
                return ConnectionResult.Failure(null, response.message())
            }
        }
    }

    private fun isConnectionAvaliable(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val active = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(active) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}