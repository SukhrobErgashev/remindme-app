package uz.gita.remindme.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.gita.remindme.data.models.Task

@Dao
interface TasksDao {

    @Insert
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("Delete from tasks_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM tasks_table ORDER BY dueCalendar ASC")
    fun getAll(): Flow<List<Task>>

    @Query("SELECT * FROM tasks_table WHERE isCompleted = 0 AND isMissed = 0 ORDER BY dueCalendar ASC")
    fun getTasks(): Flow<List<Task>>

//    @Query("Select * from tasks_table Where bodyTask like :searchQuery ORDER BY dueCalendar ASC")
//    fun searchTasks(searchQuery: String): LiveData<List<Task>>

}