package org.synac.whiteboard.presentation.whiteboard

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.synac.whiteboard.domain.model.DrawingTool
import org.synac.whiteboard.domain.model.DrawnPath
import org.synac.whiteboard.domain.model.Whiteboard
import org.synac.whiteboard.domain.repository.PathRepository
import org.synac.whiteboard.domain.repository.WhiteboardRepository
import org.synac.whiteboard.presentation.navigation.Routes
import kotlin.math.abs

class WhiteboardViewModel(
    private val pathRepository: PathRepository,
    private val whiteboardRepository: WhiteboardRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val whiteboardId = savedStateHandle.toRoute<Routes.WhiteboardScreen>().whiteboardId
    private var isFirstPath = true

    private var updatedWhiteboardId = MutableStateFlow(whiteboardId)

    private val _state = MutableStateFlow(WhiteboardState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = WhiteboardState()
    )

    init {
        whiteboardId?.let { id ->
            getWhiteboardById(id)
        }
        observePaths()
    }

    fun onEvent(event: WhiteboardEvent) {
        when (event) {
            is WhiteboardEvent.StartDrawing -> {
                if (isFirstPath) {
                    upsertWhiteboard()
                    isFirstPath = false
                }
                _state.update {
                    it.copy(startingOffset = event.offset)
                }
            }

            is WhiteboardEvent.ContinueDrawing -> {
                updateContinuingOffsets(event.offset)
            }

            WhiteboardEvent.FinishDrawing -> {
                state.value.currentPath?.let { drawnPath ->
                    insertPath(drawnPath)
                }
                _state.update {
                    it.copy(currentPath = null)
                }
            }

            WhiteboardEvent.OnDrawingToolsCardClose -> {
                _state.update { it.copy(isDrawingToolsCardVisible = false) }
            }

            is WhiteboardEvent.OnDrawingToolSelected -> {
                when(event.drawingTool) {
                    DrawingTool.RECTANGLE, DrawingTool.CIRCLE, DrawingTool.TRIANGLE -> {
                        _state.update {
                            it.copy(selectedDrawingTool = event.drawingTool)
                        }
                    }
                    else -> {
                        _state.update {
                            it.copy(
                                selectedDrawingTool = event.drawingTool,
                                backgroundColor = Color.Transparent
                            )
                        }
                    }
                }
            }

            WhiteboardEvent.OnFABClick -> {
                _state.update { it.copy(isDrawingToolsCardVisible = true) }
            }

            is WhiteboardEvent.BackgroundColorChange -> {
                _state.update { it.copy(backgroundColor = event.backgroundColor) }
            }

            is WhiteboardEvent.OpacitySliderValueChange -> {
                _state.update { it.copy(opacity = event.opacity) }
            }

            is WhiteboardEvent.StrokeColorChange -> {
                _state.update { it.copy(strokeColor = event.strokeColor) }
            }

            is WhiteboardEvent.StrokeSliderValueChange -> {
                _state.update { it.copy(strokeWidth = event.strokeWidth) }
            }
        }
    }

    private fun insertPath(path: DrawnPath) {
        viewModelScope.launch {
            pathRepository.upsertPath(path)
        }
    }

    private fun getWhiteboardById(whiteboardId: Long) {
        viewModelScope.launch {
            val whiteboard = whiteboardRepository.getWhiteboardById(whiteboardId)
            whiteboard?.let {
                _state.update {
                    it.copy(
                        whiteboardName = whiteboard.name,
                        canvasColor = whiteboard.canvasColor
                    )
                }
            }
        }
    }

    private fun upsertWhiteboard() {
        viewModelScope.launch {
            val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
            val whiteboard = Whiteboard(
                name = state.value.whiteboardName,
                lastEdited = today,
                canvasColor = state.value.canvasColor,
                id = whiteboardId
            )
            val newId = whiteboardRepository.upsertWhiteboard(whiteboard)
            updatedWhiteboardId.value = newId
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observePaths() {
        viewModelScope.launch {
            updatedWhiteboardId
                .flatMapLatest { id ->
                    pathRepository.getPathsForWhiteboard(whiteboardId = id ?: -1)
                }
                .collectLatest { paths ->
                    _state.update { it.copy(paths = paths) }
                }
        }
    }

    private fun updateContinuingOffsets(offset: Offset) {

        val startOffset = state.value.startingOffset

        val updatedPath: Path? = when (state.value.selectedDrawingTool) {
            DrawingTool.PEN, DrawingTool.HIGHLIGHTER, DrawingTool.LASER_PEN, DrawingTool.ERASER -> {
                createFreehandPath(start = startOffset, end = offset)
            }

            DrawingTool.LINE -> {
                createLinePath(start = startOffset, end = offset)
            }

            DrawingTool.ARROW -> {
                null
            }

            DrawingTool.RECTANGLE -> {
                createRectanglePath(start = startOffset, end = offset)
            }

            DrawingTool.CIRCLE -> {
                createCirclePath(start = startOffset, end = offset)
            }

            DrawingTool.TRIANGLE -> {
                createTrianglePath(start = startOffset, end = offset)
            }
        }

        updatedWhiteboardId.value?.let { id ->
            _state.update {
                it.copy(
                    currentPath = updatedPath?.let { path ->
                        DrawnPath(
                            path = path,
                            drawingTool = state.value.selectedDrawingTool,
                            strokeColor = state.value.strokeColor,
                            backgroundColor = state.value.backgroundColor,
                            opacity = state.value.opacity,
                            strokeWidth = state.value.strokeWidth,
                            whiteboardId = id
                        )
                    }
                )
            }
        }
    }

    private fun createFreehandPath(start: Offset, end: Offset): Path {
        val existingPath = state.value.currentPath?.path ?: Path().apply {
            moveTo(start.x, start.y)
        }
        return Path().apply {
            addPath(existingPath)
            lineTo(end.x, end.y)
        }
    }

    private fun createLinePath(start: Offset, end: Offset): Path {
        return Path().apply {
            moveTo(start.x, start.y)
            lineTo(end.x, end.y)
        }
    }

    private fun createRectanglePath(start: Offset, end: Offset): Path {
        val width = abs(end.x - start.x)
        val height = abs(end.y - start.y)
        return Path().apply {
            addRect(Rect(offset = start, size = Size(width = width, height = height)))
        }
    }

    private fun createCirclePath(start: Offset, end: Offset): Path {
        val width = abs(end.x - start.x)
        val height = abs(end.y - start.y)
        return Path().apply {
            addOval(Rect(offset = start, size = Size(width = width, height = height)))
        }
    }

    private fun createTrianglePath(start: Offset, end: Offset): Path {

        val height = abs(end.y - start.y)
        val halfBaseWidth = abs(end.x - start.x) / 2

        val bottomLeftVertex = Offset(x = start.x - halfBaseWidth, y = start.y + height)
        val bottomRightVertex = Offset(x = start.x + halfBaseWidth, y = start.y + height)

        return Path().apply {
            moveTo(start.x, start.y)
            lineTo(bottomLeftVertex.x, bottomLeftVertex.y)
            lineTo(bottomRightVertex.x, bottomRightVertex.y)
            close()
        }
    }
}