package com.rivaldo.taskly.data.repository.dummy

import com.rivaldo.taskly.data.local.database.model.TaskEntity
import com.rivaldo.taskly.data.local.dummy.DataDummyProvider
import com.rivaldo.taskly.data.mapper.DataMapper.mapToEntity
import com.rivaldo.taskly.data.mapper.DataMapper.mapToModel
import com.rivaldo.taskly.data.repository.interfaces.TaskRepository
import com.rivaldo.taskly.domain.StatusTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskRepositoryDummy : TaskRepository {
    override suspend fun getAllTask(): Flow<List<TaskEntity>> {
        return flow {
            emit(DataDummyProvider.listTaskEntity.map { it })
        }
    }

    override suspend fun getAllTaskByStatus(status: StatusTask): Flow<List<TaskEntity>> {
        return flow {
            val list = DataDummyProvider.listTaskEntity.filter { it.status == status.name }
            emit(
                list
            )
        }
    }

    override suspend fun getTaskById(id: Int): Flow<TaskEntity?> {
        return flow {
            val task = DataDummyProvider.listTaskEntity.find { it.id == id }
            emit(task)
        }
    }

    override suspend fun searchTaskByKeyword(keyword: String): Flow<List<TaskEntity>> {
        return flow {
            val list = DataDummyProvider.listTaskEntity.filter { it.taskName.contains(keyword, true) }
            emit(list)
        }
    }

    override suspend fun markCompleteTask(id: Int): Flow<Boolean> {
        return flow {
            val task = DataDummyProvider.listTaskEntity.find { it.id == id }
            if (task != null) {
                val index = DataDummyProvider.listTaskEntity.indexOf(task)
                DataDummyProvider.listTaskEntity[index] = task.copy(status = StatusTask.COMPLETED.name)
                emit(true)
            } else {
                emit(false)
            }
        }
    }

    override suspend fun deleteTask(id: Int): Flow<Boolean> {
        return flow {
            val task = DataDummyProvider.listTaskEntity.find { it.id == id }
            if (task != null) {
                DataDummyProvider.listTaskEntity.remove(task)
                emit(true)
            } else {
                emit(false)
            }
        }
    }

    override suspend fun addTask(task: TaskEntity): Flow<Boolean> {
        return flow {
            task.id = DataDummyProvider.listTaskEntity.size + 1
            DataDummyProvider.listTaskEntity.add(task)
            emit(true)
        }
    }
}