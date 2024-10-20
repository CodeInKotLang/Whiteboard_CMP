package org.synac.whiteboard.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.synac.whiteboard.domain.model.ColorScheme
import org.synac.whiteboard.presentation.navigation.NavGraph
import org.synac.whiteboard.presentation.settings.SettingsViewModel
import org.synac.whiteboard.presentation.theme.WhiteboardTheme

@Composable
@Preview
fun App() {

    val settingsViewModel = koinViewModel<SettingsViewModel>()
    val colorScheme by settingsViewModel.currentColorScheme.collectAsStateWithLifecycle()
    val isDarkTheme = when(colorScheme) {
        ColorScheme.SYSTEM_DEFAULT -> isSystemInDarkTheme()
        ColorScheme.LIGHT -> false
        ColorScheme.DARK -> true
    }

    WhiteboardTheme(
        darkTheme = isDarkTheme
    ) {
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            NavGraph(
                navController = navController,
                innerPadding = innerPadding,
                currentScheme = colorScheme,
                onThemeSelected = { settingsViewModel.saveColorScheme(it) }
            )
        }
    }
}