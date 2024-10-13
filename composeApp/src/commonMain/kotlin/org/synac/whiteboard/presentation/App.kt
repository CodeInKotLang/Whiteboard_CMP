package org.synac.whiteboard.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.synac.whiteboard.presentation.navigation.NavGraph

@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            NavGraph(
                navController = navController,
                innerPadding = innerPadding
            )
        }
    }
}