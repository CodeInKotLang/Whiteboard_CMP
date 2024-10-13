package org.synac.whiteboard.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingsScreen(
    onBackIconClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SettingsTopBar(
            onBackIconClick = onBackIconClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsTopBar(
    modifier: Modifier = Modifier,
    onBackIconClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = "Settings") },
        navigationIcon = {
            IconButton(onClick = onBackIconClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }
        }
    )
}