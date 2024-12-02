package org.synac.whiteboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.datetime.LocalDate
import org.synac.whiteboard.domain.model.Whiteboard
import org.synac.whiteboard.presentation.dashboard.component.WhiteboardItemCard

@Preview
@Composable
fun PreviewWhiteboardItemCard() {
    WhiteboardItemCard(
        whiteboard = Whiteboard(
            name = "Drawing New",
            lastEdited = LocalDate(2025, 2, 19),
            canvasColor = Color.White
        ),
        onRenameClick = {},
        onDeleteClick = {}
    )
}