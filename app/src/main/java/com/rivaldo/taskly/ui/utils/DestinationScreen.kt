package com.rivaldo.taskly.ui.utils

enum class DestinationScreen {
    HOME,
    ADD_TASK,
    DETAIL;

    fun getRoute(): String {
        return when (this) {
            HOME -> "home"
            ADD_TASK -> "addtask"
            DETAIL -> "detail/{" + ID_TASK_KEY + "}"
        }
    }

    fun getTitle(): String {
        return when (this) {
            HOME -> "Home"
            ADD_TASK -> "Add Task"
            DETAIL -> "Detail"
        }
    }

    companion object {
        val ID_TASK_KEY = "id"
    }
}