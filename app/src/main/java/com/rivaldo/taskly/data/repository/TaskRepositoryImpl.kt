package com.rivaldo.taskly.data.repository

import com.rivaldo.taskly.data.local.database.LocalDataSource
import com.rivaldo.taskly.data.local.database.TaskDao
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

class TaskRepositoryImpl(val remoteDataSource: RemoteDataSource, val localDataSource: LocalDataSource) :
    TaskRepository {
    val scope = CoroutineScope(Dispatchers.IO)

    override fun getAllTaskByStatus(status: StatusTask): Flow<List<TaskModel>> {
        return flow {
            localDataSource.getTaskByStatus(status.name)
                .collect {
                    emit(it.map { it.mapToModel() })
                }
        }
    }

    override suspend fun getTaskById(id: Int): Flow<TaskModel?> {
        return flow {
            localDataSource.getTaskById(id)
                .collect {
                    emit(it.mapToModel())
                }
        }
    }

    override fun searchTaskByKeyword(keyword: String): Flow<List<TaskModel>> {
        return flow {
            localDataSource.getTaskByKeyword(keyword)
                .collect {
                    emit(it.map { it.mapToModel() })
                }
        }
    }

    override fun markCompleteTask(id: Int): Flow<Boolean> {
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

    override fun deleteTask(id: Int): Flow<Boolean> {
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

    override fun addTask(task: TaskModel): Flow<Boolean> {
        return flow {
            try {
                localDataSource.insertTask(task.mapToEntity())
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