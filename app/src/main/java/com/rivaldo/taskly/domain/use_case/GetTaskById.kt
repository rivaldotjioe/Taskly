package com.rivaldo.taskly.domain.use_case

import com.rivaldo.taskly.data.repository.interfaces.TaskRepository

class GetTaskById(val repository: TaskRepository) {
    operator fun invoke(id: String) = repository.getTaskById(id)
}