package com.gavincdunne.mvvm2020.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gavincdunne.mvvm2020.data.dao.TaskDao
import com.gavincdunne.mvvm2020.data.entity.Task
import com.gavincdunne.mvvm2020.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<AppDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // db operations
            val taskDao = database.get().taskDao()

            applicationScope.launch {
                taskDao.insert(Task(name = "Wash the dishes", important = true))
                taskDao.insert(Task(name = "Do the laundry"))
                taskDao.insert(Task(name = "Buy groceries"))
                taskDao.insert(Task(name = "Prepare food", important = true))
                taskDao.insert(Task(name = "Repair my bike", completed = true))
            }
        }
    }
}