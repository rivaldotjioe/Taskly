package com.rivaldo.taskly.data.remote.api

import com.rivaldo.taskly.data.remote.model.ResponseApi
import retrofit2.http.GET

interface ApiService {

    @GET("/dummyapi")
    suspend fun getDummyApi(): ResponseApi
}