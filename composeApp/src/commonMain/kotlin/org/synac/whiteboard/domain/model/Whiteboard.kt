package org.synac.whiteboard.domain.model

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.LocalDate

data class Whiteboard(
    val id: Long? = null,
    val name: String,
    val lastEdited: LocalDate,
    val canvasColor: Color
)
