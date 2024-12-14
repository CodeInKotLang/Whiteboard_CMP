package org.synac.whiteboard.presentation.whiteboard

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import org.synac.whiteboard.domain.model.ColorPaletteType
import org.synac.whiteboard.domain.model.DrawingTool

sealed class WhiteboardEvent {
    data class StartDrawing(val offset: Offset) : WhiteboardEvent()
    data class ContinueDrawing(val continuingOffset: Offset) : WhiteboardEvent()
    data object FinishDrawing : WhiteboardEvent()
    data class OnDrawingToolSelected(val drawingTool: DrawingTool) : WhiteboardEvent()
    data object OnDrawingToolsCardClose : WhiteboardEvent()
    data object OnFABClick: WhiteboardEvent()
    data class StrokeSliderValueChange(val strokeWidth: Float) : WhiteboardEvent()
    data class OpacitySliderValueChange(val opacity: Float) : WhiteboardEvent()
    data class CanvasColorChange(val canvasColor: Color) : WhiteboardEvent()
    data class StrokeColorChange(val strokeColor: Color) : WhiteboardEvent()
    data class FillColorChange(val backgroundColor: Color) : WhiteboardEvent()
    data class OnColorPaletteIconClick(val colorPaletteType: ColorPaletteType): WhiteboardEvent()
    data class OnColorSelected(val color: Color): WhiteboardEvent()
    data object ColorSelectionDialogDismiss : WhiteboardEvent()
    data object OnLaserPathAnimationComplete : WhiteboardEvent()
}