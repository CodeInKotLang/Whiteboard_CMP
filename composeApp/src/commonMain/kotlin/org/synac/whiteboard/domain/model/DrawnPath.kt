package org.synac.whiteboard.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

data class DrawnPath(
    val id: Long? = null,
    val path: Path,
    val drawingTool: DrawingTool,
    val strokeWidth: Float,
    val strokeColor: Color,
    val backgroundColor: Color,
    val opacity: Float
)
