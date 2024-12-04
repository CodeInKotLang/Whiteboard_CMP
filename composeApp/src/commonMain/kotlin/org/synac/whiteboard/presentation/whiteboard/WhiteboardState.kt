package org.synac.whiteboard.presentation.whiteboard

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import org.synac.whiteboard.domain.model.DrawingTool
import org.synac.whiteboard.domain.model.DrawnPath

data class WhiteboardState(
    val paths: List<DrawnPath> = emptyList(),
    val pathsToBeDeleted: List<DrawnPath> = emptyList(),
    val currentPath: DrawnPath? = null,
    val laserPenPath: DrawnPath? = null,
    val startingOffset: Offset = Offset.Zero,
    val selectedDrawingTool: DrawingTool = DrawingTool.PEN,
    val isDrawingToolsCardVisible: Boolean = false,
    val strokeWidth: Float = 5f,
    val opacity: Float = 100f,
    val canvasColor: Color = Color.White,
    val strokeColor: Color = Color.Black,
    val backgroundColor: Color = Color.Transparent,
    val whiteboardName: String = "Untitled",
)
