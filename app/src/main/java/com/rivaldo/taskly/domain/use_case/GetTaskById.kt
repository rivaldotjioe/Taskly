package com.rivaldo.taskly.domain.use_case

import com.rivaldo.taskly.data.repository.interfaces.TaskRepository

class GetTaskById(val repository: TaskRepository) {
    suspend operator fun invoke(id: Int) = repository.getTaskById(id)
}