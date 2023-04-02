package com.rivaldo.taskly.data.remote.api

import com.rivaldo.taskly.data.remote.model.ResponseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource (val apiService: ApiService) {
    suspend fun getDummyApi() : Flow<ResponseApi> {
        return flow {
            emit(apiService.getDummyApi())
        }
    }
}