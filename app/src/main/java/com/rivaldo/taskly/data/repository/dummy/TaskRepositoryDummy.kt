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

    override fun getTaskById(id: String): Flow<TaskModel?> {
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
}