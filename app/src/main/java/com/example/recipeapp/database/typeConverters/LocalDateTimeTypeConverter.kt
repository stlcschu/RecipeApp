package com.example.recipeapp.database.typeConverters

import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTimeTypeConverter {

    @TypeConverter
    fun localDateTimeToString(localDateTime: LocalDateTime) : String {
        return "${checkValue(localDateTime.year, "year")}-" +
                "${checkValue(localDateTime.monthValue, "")}-" +
                "${checkValue(localDateTime.dayOfMonth, "")}-" +
                "${checkValue(localDateTime.hour, "")}-" +
                "${checkValue(localDateTime.minute, "")}-" +
                checkValue(localDateTime.second, "")
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

    fun checkValue(value: Int, type: String) : String {
        when(type) {
            "year" -> {
                val stringBuilder = StringBuilder()
                val addZeros = 8 - value.toString().length
                for (i in 0 until addZeros) {
                    stringBuilder.append(0)
                }
                stringBuilder.append(value)
                return stringBuilder.toString()
            }
            else -> {
                if (value < 10) return "0${value}"
                return value.toString()
            }
        }
    }

}