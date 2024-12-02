package org.synac.whiteboard.presentation.dashboard.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.synac.whiteboard.domain.model.Whiteboard
import org.synac.whiteboard.presentation.util.formatDate
import whiteboard.composeapp.generated.resources.Res
import whiteboard.composeapp.generated.resources.img_canvas

@Composable
fun WhiteboardItemCard(
    modifier: Modifier = Modifier,
    whiteboard: Whiteboard,
    onRenameClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            painter = painterResource(Res.drawable.img_canvas),
            contentDescription = "Whiteboard"
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                text = whiteboard.name,
                maxLines = 2
            )
            Box {
                IconButton(
                    onClick = { isExpanded = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Options"
                    )
                }
                WhiteboardCardMoreOptionsMenu(
                    isExpanded = isExpanded,
                    onMenuDismiss = { isExpanded = false },
                    onRenameClick = onRenameClick,
                    onDeleteClick = onDeleteClick
                )
            }
        }
        Text(
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
            text = "Last edited: ${whiteboard.lastEdited.formatDate()}",
            style = MaterialTheme.typography.labelSmall
        )
    }
}