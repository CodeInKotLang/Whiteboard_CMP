package org.synac.whiteboard.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.synac.whiteboard.presentation.whiteboard.WhiteboardScreen
import org.synac.whiteboard.presentation.whiteboard.WhiteboardViewModel

@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        val viewModel = koinViewModel<WhiteboardViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            WhiteboardScreen(
                modifier = Modifier.padding(innerPadding),
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}