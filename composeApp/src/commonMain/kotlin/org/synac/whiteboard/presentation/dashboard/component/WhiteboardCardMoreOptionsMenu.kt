package org.synac.whiteboard.presentation.dashboard.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WhiteboardCardMoreOptionsMenu(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onMenuDismiss: () -> Unit,
    onRenameClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    DropdownMenu(
        modifier = modifier,
        expanded = isExpanded,
        onDismissRequest = { onMenuDismiss() }
    ) {
        DropdownMenuItem(
            text = { Text(text = "Rename") },
            onClick = {
                onRenameClick()
                onMenuDismiss()
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Rename")
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Delete") },
            onClick = {
                onDeleteClick()
                onMenuDismiss()
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        )
    }
}