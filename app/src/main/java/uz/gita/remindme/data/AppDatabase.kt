package uz.gita.remindme.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.gita.remindme.data.dao.BirthdaysDao
import uz.gita.remindme.data.dao.NotesDao
import uz.gita.remindme.data.dao.TasksDao
import uz.gita.remindme.data.models.Birthday
import uz.gita.remindme.data.models.Note
import uz.gita.remindme.data.models.Task
import uz.gita.remindme.utils.Converters

@Database(
    entities = [Note::class, Task::class, Birthday::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao
    abstract fun tasksDao(): TasksDao
    abstract fun birthDao(): BirthdaysDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        // If INSTANCE isn't null, return it
        // if it is,create the database
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}