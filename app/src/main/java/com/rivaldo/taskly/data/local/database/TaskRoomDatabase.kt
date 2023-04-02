package com.rivaldo.taskly.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rivaldo.taskly.data.local.database.model.TaskEntity
import com.rivaldo.taskly.domain.StatusTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

@Database(entities = [TaskEntity::class], version = 2, exportSchema = false)
abstract class TaskRoomDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        fun buildDatabase(context: Context): TaskRoomDatabase {
            return Room.databaseBuilder(
                context,
                TaskRoomDatabase::class.java, "Taskly.db"
            ).fallbackToDestructiveMigration()
                .addCallback(object: Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val scope = CoroutineScope(Dispatchers.IO)
                        scope.launch {
                            val taskDao = buildDatabase(context).taskDao()
                            taskDao.insertTask(TaskEntity(1, "Task 1", "Lorem ipsum", StatusTask.ACTIVE.name))
                            taskDao.insertTask(TaskEntity(2, "Task 2", "Lorem ipsum", StatusTask.ACTIVE.name))
                            taskDao.insertTask(TaskEntity(3, "Task 3", "Lorem ipsum", StatusTask.ACTIVE.name))
                            taskDao.insertTask(TaskEntity(4, "Task 4", "Lorem ipsum", StatusTask.COMPLETED.name))
                            taskDao.insertTask(TaskEntity(5, "Task 5", "Lorem ipsum", StatusTask.COMPLETED.name))
                        }
                    }
                })
                .build()
        }
    }
}