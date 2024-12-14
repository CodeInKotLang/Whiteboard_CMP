package org.synac.whiteboard.data.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.synac.whiteboard.data.database.entity.WhiteboardEntity
import org.synac.whiteboard.domain.model.Whiteboard

fun Whiteboard.toWhiteboardEntity(): WhiteboardEntity {
    return WhiteboardEntity(
        id = id,
        name = name,
        lastEdited = lastEdited,
        canvasColor = canvasColor.toArgb()
    )
}

fun WhiteboardEntity.toWhiteboard(): Whiteboard {
    return Whiteboard(
        id = id,
        name = name,
        lastEdited = lastEdited,
        canvasColor = Color(canvasColor)
    )
}

fun List<WhiteboardEntity>.toWhiteboardList() = this.map { it.toWhiteboard() }