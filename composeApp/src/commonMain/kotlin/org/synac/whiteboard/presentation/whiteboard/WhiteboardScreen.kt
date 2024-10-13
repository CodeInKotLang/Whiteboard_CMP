package org.synac.whiteboard.presentation.whiteboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.synac.whiteboard.domain.model.DrawnPath
import org.synac.whiteboard.presentation.theme.defaultDrawingColors
import org.synac.whiteboard.presentation.util.UiType
import org.synac.whiteboard.presentation.util.getUiType
import org.synac.whiteboard.presentation.util.rememberScreenSizeSize
import org.synac.whiteboard.presentation.whiteboard.component.CommandPaletteCard
import org.synac.whiteboard.presentation.whiteboard.component.CommandPaletteDrawerContent
import org.synac.whiteboard.presentation.whiteboard.component.DrawingToolFAB
import org.synac.whiteboard.presentation.whiteboard.component.DrawingToolsCardHorizontal
import org.synac.whiteboard.presentation.whiteboard.component.DrawingToolsCardVertical
import org.synac.whiteboard.presentation.whiteboard.component.TopBarHorizontal
import org.synac.whiteboard.presentation.whiteboard.component.TopBarVertical

@Composable
fun WhiteboardScreen(
    modifier: Modifier = Modifier,
    state: WhiteboardState,
    onEvent: (WhiteboardEvent) -> Unit,
    onHomeIconClick: () -> Unit
) {

    val screenSize = rememberScreenSizeSize()
    val uiType = screenSize.getUiType()

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    var isCommandPaletteOpen by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (uiType) {
            UiType.COMPACT -> {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        CommandPaletteDrawerContent(
                            onCloseIconClick = { scope.launch { drawerState.close() } },
                            selectedDrawingTool = state.selectedDrawingTool,
                            strokeColors = defaultDrawingColors,
                            selectedStrokeColor = state.strokeColor,
                            onStrokeColorChange = { onEvent(WhiteboardEvent.StrokeColorChange(it)) },
                            backgroundColors = defaultDrawingColors,
                            selectedBackgroundColor = state.backgroundColor,
                            onBackgroundColorChange = {
                                onEvent(
                                    WhiteboardEvent.BackgroundColorChange(
                                        it
                                    )
                                )
                            },
                            strokeSliderValue = state.strokeWidth,
                            onStrokeSliderValueChange = {
                                onEvent(WhiteboardEvent.StrokeSliderValueChange(it))
                            },
                            opacitySliderValue = state.opacity,
                            onOpacitySliderValueChange = {
                                onEvent(WhiteboardEvent.OpacitySliderValueChange(it))
                            }
                        )
                    },
                ) {
                    DrawingCanvas(
                        modifier = Modifier.fillMaxSize(),
                        state = state,
                        onEvent = onEvent
                    )
                    TopBarHorizontal(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(20.dp),
                        onHomeIconClick = onHomeIconClick,
                        onUndoIconClick = {},
                        onRedoIconClick = {},
                        onMenuIconClick = { scope.launch { drawerState.open() } }
                    )
                    DrawingToolFAB(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(20.dp),
                        isVisible = !state.isDrawingToolsCardVisible,
                        selectedTool = state.selectedDrawingTool,
                        onClick = { onEvent(WhiteboardEvent.OnFABClick) }
                    )
                    DrawingToolsCardHorizontal(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(20.dp),
                        isVisible = state.isDrawingToolsCardVisible,
                        selectedTool = state.selectedDrawingTool,
                        onToolClick = { onEvent(WhiteboardEvent.OnDrawingToolSelected(it)) },
                        onCloseIconClick = { onEvent(WhiteboardEvent.OnDrawingToolsCardClose) }
                    )
                }
            }

            else -> {
                DrawingCanvas(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    onEvent = onEvent
                )
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.TopStart)
                        .padding(20.dp)
                ) {
                    TopBarVertical(
                        onHomeIconClick = onHomeIconClick,
                        onUndoIconClick = {},
                        onRedoIconClick = {},
                        onMenuIconClick = { isCommandPaletteOpen = true }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    CommandPaletteCard(
                        isVisible = isCommandPaletteOpen,
                        onCloseIconClick = { isCommandPaletteOpen = false },
                        selectedDrawingTool = state.selectedDrawingTool,
                        strokeColors = defaultDrawingColors,
                        selectedStrokeColor = state.strokeColor,
                        onStrokeColorChange = { onEvent(WhiteboardEvent.StrokeColorChange(it)) },
                        backgroundColors = defaultDrawingColors,
                        selectedBackgroundColor = state.backgroundColor,
                        onBackgroundColorChange = { onEvent(WhiteboardEvent.BackgroundColorChange(it)) },
                        strokeSliderValue = state.strokeWidth,
                        onStrokeSliderValueChange = {
                            onEvent(WhiteboardEvent.StrokeSliderValueChange(it))
                        },
                        opacitySliderValue = state.opacity,
                        onOpacitySliderValueChange = {
                            onEvent(WhiteboardEvent.OpacitySliderValueChange(it))
                        }
                    )
                }
                DrawingToolFAB(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp),
                    isVisible = !state.isDrawingToolsCardVisible,
                    selectedTool = state.selectedDrawingTool,
                    onClick = { onEvent(WhiteboardEvent.OnFABClick) }
                )
                DrawingToolsCardVertical(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(20.dp),
                    isVisible = state.isDrawingToolsCardVisible,
                    selectedTool = state.selectedDrawingTool,
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
        state.paths.forEach { path ->
            drawCustomPath(path)
        }

        state.currentPath?.let { path ->
            drawCustomPath(path)
        }
    }
}

private fun DrawScope.drawCustomPath(path: DrawnPath) {
    val pathOpacity = path.opacity / 100

    when (path.backgroundColor) {
        Color.Transparent -> {
            drawPath(
                path = path.path,
                color = path.strokeColor.copy(alpha = pathOpacity),
                style = Stroke(width = path.strokeWidth.dp.toPx())
            )
        }

        else -> {
            drawPath(
                path = path.path,
                color = path.backgroundColor.copy(alpha = pathOpacity),
                style = Fill
            )
        }
    }
}