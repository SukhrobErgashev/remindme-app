package uz.gita.remindme.data.repository

import androidx.lifecycle.LiveData
import uz.gita.remindme.data.dao.BirthdaysDao
import uz.gita.remindme.data.models.Birthday

class BirthdaysRepository(private val dao: BirthdaysDao) {

    val getBirthdays: LiveData<List<Birthday>> = dao.getBirthdays()

    suspend fun insert(birthday: Birthday) {
        dao.insert(birthday)
    }

    suspend fun delete(birthday: Birthday) {
        dao.delete(birthday)
    }

    suspend fun update(birthday: Birthday) {
        dao.update(birthday)
    }

}