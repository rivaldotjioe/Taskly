package com.rivaldo.taskly.domain.use_case

import com.rivaldo.taskly.data.repository.interfaces.TaskRepository
import com.rivaldo.taskly.domain.StatusTask
import com.rivaldo.taskly.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

class AddTask(val repository: TaskRepository) {
    operator fun invoke(taskName: String, taskDesc: String): Flow<Boolean> {
        val task =
            TaskModel(taskName = taskName, description = taskDesc, status = StatusTask.ACTIVE)
        return repository.addTask(task)
    }
}