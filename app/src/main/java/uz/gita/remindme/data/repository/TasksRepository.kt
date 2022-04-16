package uz.gita.remindme.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.remindme.data.dao.TasksDao
import uz.gita.remindme.data.models.Task

class TasksRepository(private val dao: TasksDao) {

    fun getTasks(isCompleted: Boolean): Flow<List<Task>> {
        return if (isCompleted) {
            dao.getAll()
        } else dao.getTasks()
    }

    suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }

    suspend fun updateTask(task: Task) {
        dao.updateTask(task)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }

    //    fun searchTasks(searchQuery: String): LiveData<List<Task>> {
//        return dao.searchTasks(searchQuery)
//    }

}