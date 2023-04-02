package com.rivaldo.taskly.domain.model

import com.rivaldo.taskly.domain.StatusTask

data class TaskModel(
    var id:Int = 0,
    val taskName: String,
    val description : String? = null,
    val status: StatusTask
)
