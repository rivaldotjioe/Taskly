package com.rivaldo.taskly.data.repository.dummy

import com.rivaldo.taskly.data.local.dummy.DataDummyProvider
import com.rivaldo.taskly.data.repository.interfaces.TaskRepository
import com.rivaldo.taskly.domain.StatusTask
import com.rivaldo.taskly.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskRepositoryDummy : TaskRepository {
    override fun getAllTaskByStatus(status: StatusTask): Flow<List<TaskModel>> {
        return flow {
            val list = DataDummyProvider.listTask.filter { it.status == status }
            emit(
                list
            )
        }
    }

    override suspend fun getTaskById(id: Int): Flow<TaskModel?> {
        return flow {
            val task = DataDummyProvider.listTask.find { it.id == id }
            emit(task)
        }
    }

    override fun searchTaskByKeyword(keyword: String): Flow<List<TaskModel>> {
        return flow {
            val list = DataDummyProvider.listTask.filter { it.taskName.contains(keyword, true) }
            emit(list)
        }
    }

    override fun markCompleteTask(id: Int): Flow<Boolean> {
        return flow {
            val task = DataDummyProvider.listTask.find { it.id == id }
            if (task != null) {
                val index = DataDummyProvider.listTask.indexOf(task)
                DataDummyProvider.listTask[index] = task.copy(status = StatusTask.COMPLETED)
                emit(true)
            } else {
                emit(false)
            }
        }
    }

    override fun deleteTask(id: Int): Flow<Boolean> {
        return flow {
            val task = DataDummyProvider.listTask.find { it.id == id }
            if (task != null) {
                DataDummyProvider.listTask.remove(task)
                emit(true)
            } else {
                emit(false)
            }
        }
    }

    override fun addTask(task: TaskModel): Flow<Boolean> {
        return flow {
            task.id = DataDummyProvider.listTask.size + 1
            DataDummyProvider.listTask.add(task)

            emit(true)
        }
    }
}