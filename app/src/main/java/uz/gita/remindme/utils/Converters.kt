package uz.gita.remindme.utils

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toMillisecond(string: String): Long {
        return string.toMillisecond()
    }

    @TypeConverter
    fun fromMillisecond(long: Long): String {
        return long.toDateString()
    }
}