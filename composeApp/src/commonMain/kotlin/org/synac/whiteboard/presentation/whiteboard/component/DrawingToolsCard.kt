package org.synac.whiteboard.presentation.whiteboard.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.synac.whiteboard.domain.model.DrawingTool

@Composable
fun DrawingToolsCardHorizontal(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    selectedTool: DrawingTool,
    onToolClick: (DrawingTool) -> Unit,
    onCloseIconClick: () -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = slideInVertically(tween(durationMillis = 500)) { h -> h },
        exit = slideOutVertically(tween(durationMillis = 500)) { h -> h }
    ) {
        ElevatedCard {
            Row(
                modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyRow(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(DrawingTool.entries) { drawingTool ->
                        DrawingToolItem(
                            drawingTool = drawingTool,
                            isSelected = selectedTool == drawingTool,
                            onToolClick = { onToolClick(drawingTool) }
                        )
                    }
                }
                FilledIconButton(onClick = { onCloseIconClick() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DrawingToolsCardVertical(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    selectedTool: DrawingTool,
    onToolClick: (DrawingTool) -> Unit,
    onCloseIconClick: () -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = slideInVertically(tween(durationMillis = 500)) { h -> h },
        exit = slideOutVertically(tween(durationMillis = 500)) { h -> h }
    ) {
        ElevatedCard {
            Column(
                modifier = Modifier.padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(DrawingTool.entries) { drawingTool ->
                        DrawingToolItem(
                            drawingTool = drawingTool,
                            isSelected = selectedTool == drawingTool,
                            onToolClick = { onToolClick(drawingTool) }
                        )
                    }
                }
                FilledIconButton(onClick = { onCloseIconClick() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DrawingToolItem(
    modifier: Modifier = Modifier,
    drawingTool: DrawingTool,
    isSelected: Boolean,
    onToolClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = { onToolClick() }) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(drawingTool.res),
                contentDescription = drawingTool.name,
                tint = if (drawingTool.isColored) Color.Unspecified else LocalContentColor.current
            )
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .background(LocalContentColor.current)
                    .size(20.dp, 1.dp)
            )
        }
    }
}