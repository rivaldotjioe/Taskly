package com.rivaldo.taskly.domain

enum class StatusTask {
    ACTIVE, COMPLETED;

    fun getText(): String {
        return when (this) {
            ACTIVE -> "Active"
            COMPLETED -> "Completed"
        }
    }
}

val listIteratedStatus = listOf(
    StatusTask.ACTIVE,
    StatusTask.COMPLETED
)