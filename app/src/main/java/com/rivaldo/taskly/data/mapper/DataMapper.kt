package com.rivaldo.taskly.data.mapper

import com.rivaldo.taskly.data.local.database.model.TaskEntity
import com.rivaldo.taskly.domain.StatusTask
import com.rivaldo.taskly.domain.model.TaskModel

object DataMapper {

    fun TaskModel?.mapToEntity() = TaskEntity(
        id = this?.id ?: 0 ,
        taskName = this?.taskName ?: "" ,
        description = this?.description,
        status = this?.status?.name ?: ""
    )

    fun TaskEntity?.mapToModel() = TaskModel(
        id = this?.id ?: 0,
        taskName = this?.taskName ?: "",
        description = this?.description,
        status = StatusTask.valueOf(this?.status ?: "")
    )
}