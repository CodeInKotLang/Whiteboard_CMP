package org.synac.whiteboard.presentation.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format

fun LocalDate.formatDate(): String {
    return this.format(
        LocalDate.Format {
            dayOfMonth()
            chars("/")
            monthNumber()
            chars("/")
            year()
        }
    )
}