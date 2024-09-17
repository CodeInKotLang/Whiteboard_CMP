package org.synac.whiteboard.presentation.whiteboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.synac.whiteboard.domain.model.DrawingTool
import org.synac.whiteboard.presentation.whiteboard.component.DrawingToolFAB
import org.synac.whiteboard.presentation.whiteboard.component.DrawingToolsCard
import org.synac.whiteboard.presentation.whiteboard.component.TopBar

@Composable
fun WhiteboardScreen(
    modifier: Modifier = Modifier,
    state: WhiteboardState,
    onEvent: (WhiteboardEvent) -> Unit
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        DrawingCanvas(
            modifier = Modifier.fillMaxSize(),
            state = state,
            onEvent = onEvent
        )
        TopBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(20.dp),
            onHomeIconClick = {

            },
            onUndoIconClick = { },
            onRedoIconClick = { },
            onStrokeWidthClick = { },
            onDrawingColorClick = { },
            onBackgroundColorClick = { },
            onSettingsClick = {}
        )
        DrawingToolFAB(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            isVisible = !state.isDrawingToolsCardVisible,
            selectedTool = state.selectedTool,
            onClick = { onEvent(WhiteboardEvent.OnFABClick) }
        )
        DrawingToolsCard(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            isVisible = state.isDrawingToolsCardVisible,
            selectedTool = state.selectedTool,
            onToolClick = { onEvent(WhiteboardEvent.OnDrawingToolSelected(it)) },
            onCloseIconClick = { onEvent(WhiteboardEvent.OnDrawingToolsCardClose) }
        )
    }
}

@Composable
private fun DrawingCanvas(
    modifier: Modifier = Modifier,
    state: WhiteboardState,
    onEvent: (WhiteboardEvent) -> Unit
) {

    Canvas(
        modifier = modifier
            .background(Color.White)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        onEvent(WhiteboardEvent.StartDrawing(offset))
                    },
                    onDrag = { change, _ ->
                        val offset = Offset(x = change.position.x, y = change.position.y)
                        onEvent(WhiteboardEvent.ContinueDrawing(offset))
                    },
                    onDragEnd = {
                        onEvent(WhiteboardEvent.FinishDrawing)
                    }
                )
            }
    ) {
        state.paths.forEach { drawnPath ->
            val pathEffect = if (drawnPath.drawingTool == DrawingTool.LINE_DOTTED) {
                PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            } else null

            val drawnStyle = when (drawnPath.drawingTool) {
                DrawingTool.CIRCLE_FILLED, DrawingTool.RECTANGLE_FILLED, DrawingTool.TRIANGLE_FILLED -> {
                    Fill
                }
                else -> {
                    Stroke(width = 10f, pathEffect = pathEffect)
                }
            }

            drawPath(
                path = drawnPath.path,
                color = Color.Black,
                style = drawnStyle
            )
        }

        state.currentPath?.let { drawnPath ->
            val pathEffect = if (drawnPath.drawingTool == DrawingTool.LINE_DOTTED) {
                PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            } else null

            val drawnStyle = when (drawnPath.drawingTool) {
                DrawingTool.CIRCLE_FILLED, DrawingTool.RECTANGLE_FILLED, DrawingTool.TRIANGLE_FILLED -> {
                    Fill
                }
                else -> {
                    Stroke(width = 10f, pathEffect = pathEffect)
                }
            }

            drawPath(
                path = drawnPath.path,
                color = Color.Black,
                style = drawnStyle
            )
        }
    }
}