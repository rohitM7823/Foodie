package com.example.foodie.models

import com.example.foodie.models.apiService.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject


// use activity retained scope survived configuration changes.
@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    val rDataSource = remoteDataSource

}