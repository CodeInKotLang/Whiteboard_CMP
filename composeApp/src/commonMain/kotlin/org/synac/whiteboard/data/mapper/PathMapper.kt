package org.synac.whiteboard.data.mapper

import org.synac.whiteboard.data.local.entity.PathEntity
import org.synac.whiteboard.domain.model.DrawnPath

fun DrawnPath.toPathEntity(): PathEntity {
    return PathEntity(
        id = id,
        drawingTool = drawingTool,
        path = path
    )
}

fun PathEntity.toDrawnPath(): DrawnPath {
    return DrawnPath(
        id = id,
        drawingTool = drawingTool,
        path = path
    )
}

fun List<PathEntity>.toDrawnPaths(): List<DrawnPath> {
    return map { it.toDrawnPath() }
}