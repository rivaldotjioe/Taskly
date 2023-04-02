package com.rivaldo.taskly.data.local.database

import com.rivaldo.taskly.data.local.database.model.TaskEntity

class LocalDataSource(val taskDao: TaskDao) {
    fun getAllTask() = taskDao.getAllTask()
    fun getTaskById(id: Int) = taskDao.getTaskById(id)
    suspend fun insertTask(taskEntity: TaskEntity) = taskDao.insertTask(taskEntity)
    suspend fun updateTask(taskEntity: TaskEntity) = taskDao.updateTask(taskEntity)
    suspend fun deleteTask(id: Int) = taskDao.deleteTaskById(id)
    fun getTaskByKeyword(keyword: String) = taskDao.searchTaskByKeyword(keyword)
}