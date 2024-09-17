package org.synac.whiteboard.presentation.whiteboard

import androidx.compose.ui.geometry.Offset
import org.synac.whiteboard.domain.model.DrawingTool
import org.synac.whiteboard.domain.model.DrawnPath

data class WhiteboardState(
    val paths: List<DrawnPath> = emptyList(),
    val currentPath: DrawnPath? = null,
    val startingOffset: Offset = Offset.Zero,
    val selectedTool: DrawingTool = DrawingTool.PEN,
    val isDrawingToolsCardVisible: Boolean = false
)
