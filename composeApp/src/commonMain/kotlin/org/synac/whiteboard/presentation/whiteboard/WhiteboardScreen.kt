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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.synac.whiteboard.presentation.util.UiType
import org.synac.whiteboard.presentation.util.getUiType
import org.synac.whiteboard.presentation.util.rememberScreenSizeSize
import org.synac.whiteboard.presentation.whiteboard.component.DrawingToolFAB
import org.synac.whiteboard.presentation.whiteboard.component.DrawingToolsCardHorizontal
import org.synac.whiteboard.presentation.whiteboard.component.DrawingToolsCardVertical
import org.synac.whiteboard.presentation.whiteboard.component.TopBarHorizontal
import org.synac.whiteboard.presentation.whiteboard.component.TopBarVertical

@Composable
fun WhiteboardScreen(
    modifier: Modifier = Modifier,
    state: WhiteboardState,
    onEvent: (WhiteboardEvent) -> Unit
) {

    val screenSize = rememberScreenSizeSize()
    val uiType = screenSize.getUiType()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (uiType) {
            UiType.COMPACT -> {
                DrawingCanvas(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    onEvent = onEvent
                )
                TopBarHorizontal(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(20.dp),
                    onHomeIconClick = {},
                    onUndoIconClick = {},
                    onRedoIconClick = {},
                    onMenuIconClick = {}
                )
                DrawingToolFAB(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp),
                    isVisible = !state.isDrawingToolsCardVisible,
                    selectedTool = state.selectedTool,
                    onClick = { onEvent(WhiteboardEvent.OnFABClick) }
                )
                DrawingToolsCardHorizontal(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(20.dp),
                    isVisible = state.isDrawingToolsCardVisible,
                    selectedTool = state.selectedTool,
                    onToolClick = { onEvent(WhiteboardEvent.OnDrawingToolSelected(it)) },
                    onCloseIconClick = { onEvent(WhiteboardEvent.OnDrawingToolsCardClose) }
                )
            }

            else -> {
                DrawingCanvas(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    onEvent = onEvent
                )
                TopBarVertical(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(20.dp),
                    onHomeIconClick = {},
                    onUndoIconClick = {},
                    onRedoIconClick = {},
                    onMenuIconClick = {}
                )
                DrawingToolFAB(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp),
                    isVisible = !state.isDrawingToolsCardVisible,
                    selectedTool = state.selectedTool,
                    onClick = { onEvent(WhiteboardEvent.OnFABClick) }
                )
                DrawingToolsCardVertical(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(20.dp),
                    isVisible = state.isDrawingToolsCardVisible,
                    selectedTool = state.selectedTool,
                    onToolClick = { onEvent(WhiteboardEvent.OnDrawingToolSelected(it)) },
                    onCloseIconClick = { onEvent(WhiteboardEvent.OnDrawingToolsCardClose) }
                )
            }
        }

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
            drawPath(
                path = drawnPath.path,
                color = Color.Black,
                style = Stroke(width = 10f)
            )
        }

        state.currentPath?.let { drawnPath ->
            drawPath(
                path = drawnPath.path,
                color = Color.Black,
                style = Stroke(width = 10f)
            )
        }
    }
}