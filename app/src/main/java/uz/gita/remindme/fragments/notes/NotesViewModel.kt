package uz.gita.remindme.fragments.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.remindme.data.AppDatabase
import uz.gita.remindme.data.models.Note
import uz.gita.remindme.data.repository.NotesRepository

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NotesRepository(AppDatabase.getDatabase(application).notesDao())

    val getAllNotes: LiveData<List<Note>> = repository.getAllNotes

    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchNotes(searchQuery: String) : LiveData<List<Note>> {
        return repository.searchNotes(searchQuery)
    }

}