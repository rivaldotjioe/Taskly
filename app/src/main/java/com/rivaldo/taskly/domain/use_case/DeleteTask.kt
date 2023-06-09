package com.rivaldo.taskly.domain.use_case

import com.rivaldo.taskly.data.repository.interfaces.TaskRepository

class DeleteTask(val repository: TaskRepository) {
    suspend operator fun invoke(id: Int) = repository.deleteTask(id)
}