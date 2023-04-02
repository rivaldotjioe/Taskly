package com.rivaldo.taskly.data.local.database.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rivaldo.taskly.domain.StatusTask
import kotlinx.android.extensions.ContainerOptions
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "task_name")
    var taskName: String,
    @ColumnInfo(name = "description")
    var description: String? = null,
    @ColumnInfo(name = "status")
    var status: String
) : Parcelable
