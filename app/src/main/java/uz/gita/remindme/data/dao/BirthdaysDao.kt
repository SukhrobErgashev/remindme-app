package uz.gita.remindme.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import uz.gita.remindme.data.models.Birthday

@Dao
interface BirthdaysDao {
    @Insert
    suspend fun insert(birthday: Birthday)

    @Delete
    suspend fun delete(birthday: Birthday)

    @Update
    suspend fun update(birthday: Birthday)

    @Query("Select * From birthdays_table Order By id DESC")
    fun getBirthdays(): LiveData<List<Birthday>>
}