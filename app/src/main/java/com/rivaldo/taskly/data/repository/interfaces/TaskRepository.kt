package com.rivaldo.taskly.data.repository.interfaces

import com.rivaldo.taskly.domain.StatusTask
import com.rivaldo.taskly.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTaskByStatus(status: StatusTask) : Flow<List<TaskModel>>
    suspend fun getTaskById(id: Int) : Flow<TaskModel?>
    fun searchTaskByKeyword(keyword: String) : Flow<List<TaskModel>>
    fun markCompleteTask(id: Int) : Flow<Boolean>
    fun deleteTask(id: Int) : Flow<Boolean>
    fun addTask(task: TaskModel) : Flow<Boolean>
}