package com.rivaldo.taskly.data.repository

import com.rivaldo.taskly.data.local.database.LocalDataSource
import com.rivaldo.taskly.data.local.database.TaskDao
import com.rivaldo.taskly.data.local.database.model.TaskEntity
import com.rivaldo.taskly.data.mapper.DataMapper.mapToEntity
import com.rivaldo.taskly.data.mapper.DataMapper.mapToModel
import com.rivaldo.taskly.data.remote.ApiResponseCode
import com.rivaldo.taskly.data.remote.api.ApiService
import com.rivaldo.taskly.data.remote.api.RemoteDataSource
import com.rivaldo.taskly.data.remote.model.ResponseApi
import com.rivaldo.taskly.data.repository.interfaces.TaskRepository
import com.rivaldo.taskly.domain.StatusTask
import com.rivaldo.taskly.domain.model.TaskModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class TaskRepositoryImpl(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource
) :
    TaskRepository {

    override suspend fun getAllTaskByStatus(status: StatusTask): Flow<List<TaskEntity>> {
        return flow {
            localDataSource.getTaskByStatus(status.name)
                .collect {
                    emit(it)
                }
        }
    }

    override suspend fun getAllTask(): Flow<List<TaskEntity>> {
        return flow {
            localDataSource.getAllTask()
                .collect {
                    emit(it)
                }
        }
    }

    override suspend fun getTaskById(id: Int): Flow<TaskEntity?> {
        return flow {
            localDataSource.getTaskById(id)
                .collect {
                    emit(it)
                }
        }
    }

    override suspend fun searchTaskByKeyword(keyword: String): Flow<List<TaskEntity>> {
        return flow {
            localDataSource.getTaskByKeyword(keyword)
                .collect {
                    emit(it)
                }
        }
    }

    override suspend fun markCompleteTask(id: Int): Flow<Boolean> {
        return flow {
            try {
                val taskEntity = localDataSource.getTaskById(id).first()
                localDataSource.updateTask(taskEntity.copy(status = StatusTask.COMPLETED.name))
                val response = remoteDataSource.getDummyApi().first()
                emit(isApiResponseSuccess(response))
            } catch (e: Exception) {
                emit(false)
            }
        }
    }

    override suspend fun deleteTask(id: Int): Flow<Boolean> {
        return flow {
            try {
                localDataSource.deleteTask(id)
                val response = remoteDataSource.getDummyApi().first()
                emit(isApiResponseSuccess(response))
            } catch (e: Exception) {
                emit(false)
            }
        }
    }

    override suspend fun addTask(task: TaskEntity): Flow<Boolean> {
        return flow {
            try {
                localDataSource.insertTask(task)
                val response = remoteDataSource.getDummyApi().first()
                emit(isApiResponseSuccess(response))
            } catch (e: Exception) {
                emit(false)
            }
        }
    }

    private fun isApiResponseSuccess(response: ResponseApi): Boolean {
        return response.status == ApiResponseCode.SUCCESS
    }
}