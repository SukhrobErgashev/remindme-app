package uz.gita.remindme.fragments.tasks

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.gita.remindme.data.AppDatabase
import uz.gita.remindme.data.models.Task
import uz.gita.remindme.data.repository.TasksRepository

class TasksViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        TasksRepository(AppDatabase.getDatabase(application.applicationContext).tasksDao())

    private var _currentList = MutableLiveData<List<Task>>()
    val currentList: LiveData<List<Task>>
        get() = _currentList

//    init {
//        getTasks(false)
//    }

    fun getTasks(completed: Boolean) = viewModelScope.launch {
        repository.getTasks(completed).collect {
            _currentList.value = it
        }
    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

//    fun searchTasks(searchQuery: String): LiveData<List<Task>> {
//        return repository.searchTasks(searchQuery)
//    }

}