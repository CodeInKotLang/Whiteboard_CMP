package org.synac.whiteboard

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.synac.whiteboard.di.initKoin
import org.synac.whiteboard.presentation.App

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Whiteboard",
        ) {
            App()
        }
    }
}