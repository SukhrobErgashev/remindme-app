package uz.gita.remindme.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import uz.gita.remindme.data.models.Note

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAll()

    @Query("Select * From notes_table Where bodyNote like :searchQuery order by id desc")
    fun searchNotes(searchQuery: String): LiveData<List<Note>>

}