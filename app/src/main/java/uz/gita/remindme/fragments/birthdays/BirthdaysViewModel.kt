package uz.gita.remindme.fragments.birthdays

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.gita.remindme.data.AppDatabase
import uz.gita.remindme.data.models.Birthday
import uz.gita.remindme.data.repository.BirthdaysRepository

class BirthdaysViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BirthdaysRepository(AppDatabase.getDatabase(application).birthDao())

    val getBirthdays: LiveData<List<Birthday>> = repository.getBirthdays

    fun insert(birthday: Birthday) = viewModelScope.launch {
        repository.insert(birthday)
    }

    fun delete(birthday: Birthday) = viewModelScope.launch {
        repository.delete(birthday)
    }

    fun update(birthday: Birthday) = viewModelScope.launch {
        repository.update(birthday)
    }
}