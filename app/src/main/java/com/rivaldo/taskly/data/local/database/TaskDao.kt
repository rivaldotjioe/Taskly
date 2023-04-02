package com.rivaldo.taskly.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rivaldo.taskly.data.local.database.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Query("SELECT * FROM task_table")
    fun getAllTask(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE id = :id")
    fun getTaskById(id: Int): Flow<TaskEntity>

    @Query("DELETE FROM task_table WHERE id = :id")
    suspend fun deleteTaskById(id: Int)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("SELECT * FROM task_table WHERE status = :status")
    fun getTaskByStatus(status: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE task_name LIKE '%' || :keyword || '%'")
    fun searchTaskByKeyword(keyword: String): Flow<List<TaskEntity>>

}