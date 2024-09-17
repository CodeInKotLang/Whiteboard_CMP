package org.synac.whiteboard.presentation.whiteboard.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import whiteboard.composeapp.generated.resources.Res
import whiteboard.composeapp.generated.resources.ic_redo
import whiteboard.composeapp.generated.resources.ic_undo

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onHomeIconClick: () -> Unit,
    onUndoIconClick: () -> Unit,
    onRedoIconClick: () -> Unit,
    onStrokeWidthClick: () -> Unit,
    onDrawingColorClick: () -> Unit,
    onBackgroundColorClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {

    var isMoreOptionsMenuOpen by rememberSaveable { mutableStateOf(false) }

    Row(modifier = modifier) {
        FilledIconButton(onClick = { onHomeIconClick() }) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Home",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(25.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        FilledIconButton(onClick = { onUndoIconClick() }) {
            Icon(
                painter = painterResource(Res.drawable.ic_undo),
                contentDescription = "Undo",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(25.dp)
            )
        }
        FilledIconButton(onClick = { onRedoIconClick() }) {
            Icon(
                painter = painterResource(Res.drawable.ic_redo),
                contentDescription = "Redo",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(25.dp)
            )
        }
        Box {
            FilledIconButton(onClick = { isMoreOptionsMenuOpen = true }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More Options",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(25.dp)
                )
            }
            MoreOptionsMenu(
                isExpanded = isMoreOptionsMenuOpen,
                onMenuDismiss = { isMoreOptionsMenuOpen = false },
                onStrokeWidthClick = onStrokeWidthClick,
                onDrawingColorClick = onDrawingColorClick,
                onBackgroundColorClick = onBackgroundColorClick,
                onSettingsClick = onSettingsClick
            )
        }
    }
}

@Composable
private fun MoreOptionsMenu(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onMenuDismiss: () -> Unit,
    onStrokeWidthClick: () -> Unit,
    onDrawingColorClick: () -> Unit,
    onBackgroundColorClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    DropdownMenu(
        modifier = modifier,
        expanded = isExpanded,
        onDismissRequest = { onMenuDismiss() }
    ) {
        DropdownMenuItem(
            text = { Text(text = "Stroke Width") },
            onClick = {
                onStrokeWidthClick()
                onMenuDismiss()
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Stoke Width")
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Drawing Color") },
            onClick = {
                onDrawingColorClick()
                onMenuDismiss()
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Stoke Width")
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Background Color") },
            onClick = {
                onBackgroundColorClick()
                onMenuDismiss()
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Stoke Width")
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Settings") },
            onClick = {
                onSettingsClick()
                onMenuDismiss()
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Stoke Width")
            }
        )
    }
}