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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.synac.whiteboard.domain.model.ColorPaletteType
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
                _state.update { it.copy(startingOffset = event.offset) }
            }

            is WhiteboardEvent.ContinueDrawing -> {
                updateContinuingOffsets(event.continuingOffset)
            }

            WhiteboardEvent.FinishDrawing -> {
                state.value.currentPath?.let { drawnPath ->
                    when (drawnPath.drawingTool) {
                        DrawingTool.ERASER -> {
                            deletePaths(state.value.pathsToBeDeleted)
                        }

                        DrawingTool.LASER_PEN -> {
                            _state.update { it.copy(laserPenPath = drawnPath) }
                        }

                        else -> {
                            insertPath(drawnPath)
                        }
                    }
                }
                _state.update {
                    it.copy(
                        currentPath = null,
                        pathsToBeDeleted = emptyList()
                    )
                }
            }

            WhiteboardEvent.OnDrawingToolsCardClose -> {
                _state.update { it.copy(isDrawingToolsCardVisible = false) }
            }

            is WhiteboardEvent.OnDrawingToolSelected -> {
                when (event.drawingTool) {
                    DrawingTool.RECTANGLE, DrawingTool.CIRCLE, DrawingTool.TRIANGLE -> {
                        _state.update {
                            it.copy(selectedDrawingTool = event.drawingTool)
                        }
                    }

                    else -> {
                        _state.update {
                            it.copy(
                                selectedDrawingTool = event.drawingTool,
                                fillColor = Color.Transparent
                            )
                        }
                    }
                }
            }

            WhiteboardEvent.OnFABClick -> {
                _state.update { it.copy(isDrawingToolsCardVisible = true) }
            }

            is WhiteboardEvent.FillColorChange -> {
                _state.update { it.copy(fillColor = event.backgroundColor) }
            }

            is WhiteboardEvent.OpacitySliderValueChange -> {
                _state.update { it.copy(opacity = event.opacity) }
            }

            is WhiteboardEvent.CanvasColorChange -> {
                _state.update { it.copy(canvasColor = event.canvasColor) }
                upsertWhiteboard()
            }

            is WhiteboardEvent.StrokeColorChange -> {
                _state.update { it.copy(strokeColor = event.strokeColor) }
            }

            is WhiteboardEvent.StrokeSliderValueChange -> {
                _state.update { it.copy(strokeWidth = event.strokeWidth) }
            }

            WhiteboardEvent.OnLaserPathAnimationComplete -> {
                _state.update { it.copy(laserPenPath = null) }
            }

            is WhiteboardEvent.OnColorPaletteIconClick -> {
                _state.update {
                    it.copy(
                        isColorSelectionDialogOpen = true,
                        selectedColorPaletteType = event.colorPaletteType
                    )
                }
            }

            WhiteboardEvent.ColorSelectionDialogDismiss -> {
                _state.update { it.copy(isColorSelectionDialogOpen = false) }
            }

            is WhiteboardEvent.OnColorSelected -> {
                val state = state.value
                val color = event.color
                when(state.selectedColorPaletteType) {
                    ColorPaletteType.CANVAS -> {
                        _state.update { it.copy(canvasColor = color) }
                        upsertWhiteboard()
                    }
                    ColorPaletteType.STROKE -> {
                        _state.update { it.copy(strokeColor = color) }
                    }
                    ColorPaletteType.FILL -> {
                        _state.update { it.copy(fillColor = color) }
                    }
                }
            }
        }
    }

    private fun insertPath(path: DrawnPath) {
        viewModelScope.launch {
            pathRepository.upsertPath(path)
        }
    }

    private fun deletePaths(paths: List<DrawnPath>) {
        viewModelScope.launch {
            paths.forEach { path ->
                pathRepository.deletePath(path)
            }
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
                id = updatedWhiteboardId.value
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

    private fun updateContinuingOffsets(continuingOffset: Offset) {

        val startOffset = state.value.startingOffset

        val updatedPath: Path? = when (state.value.selectedDrawingTool) {
            DrawingTool.PEN, DrawingTool.HIGHLIGHTER, DrawingTool.LASER_PEN -> {
                createFreehandPath(start = startOffset, continuingOffset = continuingOffset)
            }

            DrawingTool.ERASER -> {
                updatePathsToBeDeleted(start = startOffset, continuingOffset = continuingOffset)
                createEraserPath(continuingOffset = continuingOffset)
            }

            DrawingTool.LINE -> {
                createLinePath(start = startOffset, continuingOffset = continuingOffset)
            }

            DrawingTool.ARROW -> null

            DrawingTool.RECTANGLE -> {
                createRectanglePath(start = startOffset, continuingOffset = continuingOffset)
            }

            DrawingTool.CIRCLE -> {
                createCirclePath(start = startOffset, continuingOffset = continuingOffset)
            }

            DrawingTool.TRIANGLE -> {
                createTrianglePath(start = startOffset, continuingOffset = continuingOffset)
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
                            fillColor = state.value.fillColor,
                            opacity = state.value.opacity,
                            strokeWidth = state.value.strokeWidth,
                            whiteboardId = id
                        )
                    }
                )
            }
        }
    }

    private fun createEraserPath(continuingOffset: Offset): Path {
        return Path().apply {
            addOval(Rect(center = continuingOffset, radius = 5f))
        }
    }

    private fun createFreehandPath(start: Offset, continuingOffset: Offset): Path {
        val existingPath = state.value.currentPath?.path ?: Path().apply {
            moveTo(start.x, start.y)
        }
        return Path().apply {
            addPath(existingPath)
            lineTo(continuingOffset.x, continuingOffset.y)
        }
    }

    private fun createLinePath(start: Offset, continuingOffset: Offset): Path {
        return Path().apply {
            moveTo(start.x, start.y)
            lineTo(continuingOffset.x, continuingOffset.y)
        }
    }

    private fun createRectanglePath(start: Offset, continuingOffset: Offset): Path {
        val width = abs(continuingOffset.x - start.x)
        val height = abs(continuingOffset.y - start.y)
        return Path().apply {
            addRect(Rect(offset = start, size = Size(width = width, height = height)))
        }
    }

    private fun createCirclePath(start: Offset, continuingOffset: Offset): Path {
        val width = continuingOffset.x - start.x
        val height = continuingOffset.y - start.y
        return Path().apply {
            addOval(Rect(offset = start, size = Size(width = width, height = height)))
        }
    }

    private fun createTrianglePath(start: Offset, continuingOffset: Offset): Path {
        val height = continuingOffset.y - start.y
        val baseWidth = continuingOffset.x - start.x
        val remainingVertex = Offset(x = start.x - baseWidth, y = start.y + height)

        return Path().apply {
            moveTo(start.x, start.y)
            lineTo(continuingOffset.x, continuingOffset.y)
            lineTo(remainingVertex.x, remainingVertex.y)
            close()
        }
    }

    private fun updatePathsToBeDeleted(start: Offset, continuingOffset: Offset) {
        val pathsToBeDeleted = state.value.pathsToBeDeleted.toMutableList()
        state.value.paths.forEach { drawnPath ->
            val bounds = drawnPath.path.getBounds()
            if (bounds.contains(start) || bounds.contains(continuingOffset)) {
                if (!pathsToBeDeleted.contains(drawnPath)) {
                    pathsToBeDeleted.add(drawnPath)
                }
            }
        }
        _state.update { it.copy(pathsToBeDeleted = pathsToBeDeleted) }
    }
}



