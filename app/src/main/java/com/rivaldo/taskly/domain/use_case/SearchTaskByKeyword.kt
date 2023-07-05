package com.rivaldo.taskly.domain.use_case

import com.rivaldo.taskly.data.mapper.DataMapper.mapToModel
import com.rivaldo.taskly.data.repository.interfaces.TaskRepository
import com.rivaldo.taskly.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class SearchTaskByKeyword(val repository: TaskRepository) {
    suspend operator fun invoke(keyword: String): Flow<List<TaskModel>> {
        if (keyword.isEmpty()) return repository.getAllTask()
            .mapNotNull { it.map { it.mapToModel() } }
        return repository.searchTaskByKeyword(keyword).mapNotNull { it.map { it.mapToModel() } }
    }
}