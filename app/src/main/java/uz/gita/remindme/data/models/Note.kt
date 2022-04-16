package uz.gita.remindme.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes_table")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var titleNote: String,
    var bodyNote: String,
    var timeNote: String,
    var dateNote: String
) : Parcelable
