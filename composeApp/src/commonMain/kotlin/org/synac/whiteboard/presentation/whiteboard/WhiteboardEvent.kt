package org.synac.whiteboard.presentation.whiteboard

import androidx.compose.ui.geometry.Offset
import org.synac.whiteboard.domain.model.DrawingTool

sealed class WhiteboardEvent {
    data class StartDrawing(val offset: Offset) : WhiteboardEvent()
    data class ContinueDrawing(val offset: Offset) : WhiteboardEvent()
    data object FinishDrawing : WhiteboardEvent()
    data class OnDrawingToolSelected(val tool: DrawingTool) : WhiteboardEvent()
    data object OnDrawingToolsCardClose : WhiteboardEvent()
    data object OnFABClick: WhiteboardEvent()
}