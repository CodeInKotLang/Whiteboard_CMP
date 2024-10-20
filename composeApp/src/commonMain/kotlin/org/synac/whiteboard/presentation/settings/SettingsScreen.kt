package org.synac.whiteboard.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.synac.whiteboard.domain.model.ColorScheme
import org.synac.whiteboard.presentation.settings.component.ColorSchemeDialog
import whiteboard.composeapp.generated.resources.Res
import whiteboard.composeapp.generated.resources.ic_theme

@Composable
fun SettingsScreen(
    onBackIconClick: () -> Unit,
    currentScheme: ColorScheme,
    onThemeSelected: (ColorScheme) -> Unit
) {

    var isColorSchemeDialogOpen by rememberSaveable { mutableStateOf(false) }

    ColorSchemeDialog(
        isOpen = isColorSchemeDialogOpen,
        onDismiss = { isColorSchemeDialogOpen = false },
        currentScheme = currentScheme,
        onThemeSelected = onThemeSelected
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SettingsTopBar(onBackIconClick = onBackIconClick)
        ListItem(
            modifier = Modifier.clickable { isColorSchemeDialogOpen = true },
            headlineContent = { Text(text = "Color Scheme") },
            supportingContent = { Text(text = currentScheme.label) },
            leadingContent = {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(Res.drawable.ic_theme),
                    contentDescription = "color scheme"
                )
            }
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