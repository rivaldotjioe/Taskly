package com.rivaldo.taskly.domain

enum class StatusTask {
    ACTIVE, COMPLETED;

    fun getText(): String {
        return when (this) {
            ACTIVE -> "Active"
            COMPLETED -> "Completed"
        }
    }

    companion object {
        fun fromString(text: String): StatusTask? {
            return when (text) {
                "Active" -> ACTIVE
                "Completed" -> COMPLETED
                else -> null
            }
        }
    }
}

val listIteratedStatus = listOf(
    StatusTask.ACTIVE,
    StatusTask.COMPLETED
)

val listRadioStatus = listOf(
    "All",
    StatusTask.ACTIVE.getText(),
    StatusTask.COMPLETED.getText()
)
