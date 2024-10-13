package org.synac.whiteboard.presentation.whiteboard

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import org.synac.whiteboard.domain.model.DrawingTool

sealed class WhiteboardEvent {
    data class StartDrawing(val offset: Offset) : WhiteboardEvent()
    data class ContinueDrawing(val offset: Offset) : WhiteboardEvent()
    data object FinishDrawing : WhiteboardEvent()
    data class OnDrawingToolSelected(val drawingTool: DrawingTool) : WhiteboardEvent()
    data object OnDrawingToolsCardClose : WhiteboardEvent()
    data object OnFABClick: WhiteboardEvent()
    data class StrokeSliderValueChange(val strokeWidth: Float) : WhiteboardEvent()
    data class OpacitySliderValueChange(val opacity: Float) : WhiteboardEvent()
    data class StrokeColorChange(val strokeColor: Color) : WhiteboardEvent()
    data class BackgroundColorChange(val backgroundColor: Color) : WhiteboardEvent()
}