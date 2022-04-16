package uz.gita.remindme.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "birthdays_table")
@Parcelize
data class Birthday(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var date: String,
    var gender: String,
    var remindMe: Boolean
) : Parcelable