package org.synac.whiteboard.data.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.synac.whiteboard.data.database.entity.PathEntity
import org.synac.whiteboard.domain.model.DrawnPath

fun DrawnPath.toPathEntity(): PathEntity {
    return PathEntity(
        id = id,
        drawingTool = drawingTool,
        path = path,
        strokeWidth = strokeWidth,
        opacity = opacity,
        strokeColor = strokeColor.toArgb(),
        fillColor = fillColor.toArgb(),
        whiteboardId = whiteboardId
    )
}

fun PathEntity.toDrawnPath(): DrawnPath {
    return DrawnPath(
        id = id,
        drawingTool = drawingTool,
        path = path,
        strokeWidth = strokeWidth,
        opacity = opacity,
        strokeColor = Color(strokeColor),
        fillColor = Color(fillColor),
        whiteboardId = whiteboardId
    )
}

fun List<PathEntity>.toDrawnPathList() = map { it.toDrawnPath() }