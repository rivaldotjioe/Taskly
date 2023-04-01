package com.rivaldo.taskly.data.local.dummy

import com.rivaldo.taskly.domain.StatusTask
import com.rivaldo.taskly.domain.model.TaskModel

object DataDummyProvider {
    val listTask = mutableListOf(
        TaskModel("1", "Task 1", "Lorem ipsum", StatusTask.ACTIVE),
        TaskModel("2", "Task 2", "Lorem ipsum", StatusTask.ACTIVE),
        TaskModel("3", "Task 3", "Lorem ipsum", StatusTask.ACTIVE),
        TaskModel("4", "Task 4", "Lorem ipsum", StatusTask.COMPLETED),
        TaskModel("3", "Task 3", "Lorem ipsum", StatusTask.COMPLETED),
    )
}