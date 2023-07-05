package com.rivaldo.taskly.data.repository.interfaces

import com.rivaldo.taskly.data.local.database.model.TaskEntity
import com.rivaldo.taskly.domain.StatusTask
import com.rivaldo.taskly.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getAllTask() : Flow<List<TaskEntity>>
    suspend fun getAllTaskByStatus(status: StatusTask) : Flow<List<TaskEntity>>
    suspend fun getTaskById(id: Int) : Flow<TaskEntity?>
    suspend fun searchTaskByKeyword(keyword: String) : Flow<List<TaskEntity>>
    suspend fun markCompleteTask(id: Int) : Flow<Boolean>
    suspend fun deleteTask(id: Int) : Flow<Boolean>
    suspend fun addTask(task: TaskEntity) : Flow<Boolean>
}