package com.example.recipeapp.database.typeConverters

import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTimeTypeConverter {

    @TypeConverter
    fun localDateTimeToString(localDateTime: LocalDateTime) : String {
        return "${localDateTime.year}-${localDateTime.monthValue}-${localDateTime.dayOfMonth}-${localDateTime.hour}-${localDateTime.minute}-${localDateTime.second}"
    }

    @TypeConverter
    fun stringToLocalDateTime(string: String) : LocalDateTime {
        val values = string.split("-")
        return LocalDateTime.of(
            values[0].toInt(),
            values[1].toInt(),
            values[2].toInt(),
            values[3].toInt(),
            values[4].toInt(),
            values[5].toInt()
        )
    }

}