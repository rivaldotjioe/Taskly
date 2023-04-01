package com.rivaldo.taskly.domain.use_case

import com.rivaldo.taskly.data.repository.interfaces.TaskRepository

class MarkCompleteTask(val repository: TaskRepository) {
    operator fun invoke(id: String) = repository.markCompleteTask(id)
}