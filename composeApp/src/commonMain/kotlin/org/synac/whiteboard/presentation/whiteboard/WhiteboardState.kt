package org.synac.whiteboard.presentation.whiteboard

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import org.synac.whiteboard.domain.model.ColorPaletteType
import org.synac.whiteboard.domain.model.DrawingTool
import org.synac.whiteboard.domain.model.DrawnPath
import org.synac.whiteboard.presentation.theme.defaultCanvasColors
import org.synac.whiteboard.presentation.theme.defaultDrawingColors

data class WhiteboardState(
    val paths: List<DrawnPath> = emptyList(),
    val pathsToBeDeleted: List<DrawnPath> = emptyList(),
    val currentPath: DrawnPath? = null,
    val laserPenPath: DrawnPath? = null,
    val startingOffset: Offset = Offset.Zero,
    val selectedDrawingTool: DrawingTool = DrawingTool.PEN,
    val isDrawingToolsCardVisible: Boolean = false,
    val isColorSelectionDialogOpen: Boolean = false,
    val strokeWidth: Float = 5f,
    val opacity: Float = 100f,
    val selectedColorPaletteType: ColorPaletteType = ColorPaletteType.STROKE,
    val canvasColor: Color = Color.White,
    val strokeColor: Color = Color.Black,
    val fillColor: Color = Color.Transparent,
    val whiteboardName: String = "Untitled",
    val preferredStrokeColors: List<Color> = defaultDrawingColors,
    val preferredFillColors: List<Color> = defaultDrawingColors,
    val preferredCanvasColors: List<Color> = defaultCanvasColors,
)
