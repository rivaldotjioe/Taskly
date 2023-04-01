package com.rivaldo.taskly.domain.use_case

import com.rivaldo.taskly.data.repository.interfaces.TaskRepository
import com.rivaldo.taskly.domain.StatusTask
import com.rivaldo.taskly.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

class GetAllTaskByStatus(
    val repository: TaskRepository
) {
    operator fun invoke(status: StatusTask) : Flow<List<TaskModel>> {
        return repository.getAllTaskByStatus(status)
    }
}