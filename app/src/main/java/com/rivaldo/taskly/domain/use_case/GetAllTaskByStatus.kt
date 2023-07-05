package com.rivaldo.taskly.domain.use_case

import com.rivaldo.taskly.data.mapper.DataMapper.mapToModel
import com.rivaldo.taskly.data.repository.interfaces.TaskRepository
import com.rivaldo.taskly.domain.StatusTask
import com.rivaldo.taskly.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class GetAllTaskByStatus(
    val repository: TaskRepository
) {
    suspend operator fun invoke(status: StatusTask) : Flow<List<TaskModel>> {
        return repository.getAllTaskByStatus(status).mapNotNull { it.mapToModel() }
    }
}