package com.rivaldo.taskly.domain.use_case

import com.rivaldo.taskly.data.mapper.DataMapper.mapToModel
import com.rivaldo.taskly.data.repository.interfaces.TaskRepository
import kotlinx.coroutines.flow.map

class GetTaskById(val repository: TaskRepository) {
    suspend operator fun invoke(id: Int) = repository.getTaskById(id).map { it.mapToModel() }
}