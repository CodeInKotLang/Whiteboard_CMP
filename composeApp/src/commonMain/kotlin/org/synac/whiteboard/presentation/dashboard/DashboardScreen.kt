package org.synac.whiteboard.presentation.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(
    onSettingsIconClick: () -> Unit,
    onAddNewWhiteboardClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        DashboardTopBar(
            modifier = Modifier.align(Alignment.TopCenter),
            onSettingsIconClick = onSettingsIconClick
        )
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = onAddNewWhiteboardClick
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "New Whiteboard"
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardTopBar(
    modifier: Modifier = Modifier,
    onSettingsIconClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = "Dashboard") },
        actions = {
            IconButton(onClick = onSettingsIconClick) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings"
                )
            }
        }
    )
}