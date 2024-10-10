package org.synac.whiteboard.data.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.synac.whiteboard.data.local.entity.PathEntity
import org.synac.whiteboard.domain.model.DrawnPath

fun DrawnPath.toPathEntity(): PathEntity {
    return PathEntity(
        id = id,
        drawingTool = drawingTool,
        path = path,
        strokeWidth = strokeWidth,
        opacity = opacity,
        strokeColor = strokeColor.toArgb(),
        backgroundColor = backgroundColor.toArgb()
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
        backgroundColor = Color(backgroundColor)
    )
}

fun List<PathEntity>.toDrawnPaths(): List<DrawnPath> {
    return map { it.toDrawnPath() }
}