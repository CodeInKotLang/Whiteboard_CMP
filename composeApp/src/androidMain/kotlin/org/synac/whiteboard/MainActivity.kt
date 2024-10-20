package org.synac.whiteboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.synac.whiteboard.domain.model.ColorScheme
import org.synac.whiteboard.presentation.App
import org.synac.whiteboard.presentation.settings.component.ColorSchemeDialog

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}


@Preview
@Composable
private fun Prev() {
    ColorSchemeDialog(
        isOpen = true,
        onDismiss = {},
        currentScheme = ColorScheme.SYSTEM_DEFAULT,
        onThemeSelected = {}
    )
}