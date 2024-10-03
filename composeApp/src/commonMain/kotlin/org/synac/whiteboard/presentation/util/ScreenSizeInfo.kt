package org.synac.whiteboard.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ScreenSizeInfo(
    val heightDp: Dp,
    val widthDp: Dp
)

@Composable
expect fun rememberScreenSizeSize(): ScreenSizeInfo

fun ScreenSizeInfo.getUiType(): UiType {
    return when(widthDp) {
        in 0.dp..600.dp -> UiType.COMPACT
        in 601.dp..840.dp -> UiType.MEDIUM
        else -> UiType.EXPANDED
    }
}

enum class UiType {
    COMPACT,
    MEDIUM,
    EXPANDED
}

