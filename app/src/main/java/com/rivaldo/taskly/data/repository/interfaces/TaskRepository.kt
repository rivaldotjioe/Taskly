package com.rivaldo.taskly.data.repository.interfaces

import com.rivaldo.taskly.domain.StatusTask
import com.rivaldo.taskly.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTaskByStatus(status: StatusTask) : Flow<List<TaskModel>>
    fun getTaskById(id: String) : Flow<TaskModel?>
    fun searchTaskByKeyword(keyword: String) : Flow<List<TaskModel>>
}