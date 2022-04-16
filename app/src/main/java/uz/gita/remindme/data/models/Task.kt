package uz.gita.remindme.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "tasks_table")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var titleTask: String,
    var bodyTask: String,
    var dueCalendar: Long,
    var notifyType: String = "notification",
    var isCompleted: Boolean = false,
    var isMissed: Boolean = false
) : Parcelable

