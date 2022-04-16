package uz.gita.remindme.data.repository

import androidx.lifecycle.LiveData
import uz.gita.remindme.data.dao.NotesDao
import uz.gita.remindme.data.models.Note

class NotesRepository(private val dao: NotesDao) {

    val getAllNotes: LiveData<List<Note>> = dao.getAllNotes()

    suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    suspend fun updateNote(note: Note) {
        dao.updateNote(note)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }

    fun searchNotes(searchQuery: String): LiveData<List<Note>> {
        return dao.searchNotes(searchQuery)
    }
}