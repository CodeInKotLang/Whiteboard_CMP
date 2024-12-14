package org.synac.whiteboard.data.database.convertor

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format

class LocalDateConverter {

    private val sortableDateFormat = LocalDate.Format {
        year()
        chars("/")
        monthNumber()
        chars("/")
        dayOfMonth()
    }

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.format(sortableDateFormat)
    }

    @TypeConverter
    fun toLocalDate(date: String): LocalDate {
        return LocalDate.parse(
            input = date,
            format = sortableDateFormat
        )
    }

}