package org.synac.whiteboard.presentation.settings.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.synac.whiteboard.domain.model.ColorScheme

@Composable
fun ColorSchemeDialog(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    currentScheme: ColorScheme,
    onDismiss: () -> Unit,
    onThemeSelected: (ColorScheme) -> Unit
) {

    var selectedScheme by rememberSaveable { mutableStateOf(currentScheme) }

    if (isOpen) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismiss,
            title = { Text(text = "Color Scheme") },
            text = {
                Column {
                    ColorScheme.entries.forEach { colorScheme ->
                        LabeledRadioButton(
                            selected = selectedScheme == colorScheme,
                            label = colorScheme.label,
                            onClick = { selectedScheme = colorScheme }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onThemeSelected(selectedScheme)
                        onDismiss()
                    }
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = "Close")
                }
            }
        )
    }
}

@Composable
private fun LabeledRadioButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(text = label)
    }
}