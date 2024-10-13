package org.synac.whiteboard.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    data object DashboardScreen : Routes()

    @Serializable
    data class WhiteboardScreen(val whiteboardId: Long?) : Routes()

    @Serializable
    data object SettingsScreen : Routes()
}