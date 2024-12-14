package org.synac.whiteboard.presentation.whiteboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun ColorSelectionDialog(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit
) {

    val controller = rememberColorPickerController()
    var hexInput by remember { mutableStateOf(controller.selectedColor.value.toColorHexString()) }
    var isError by remember { mutableStateOf(false) }

    fun handleHexInputChange(input: String) {
        if (input.length == 6 && input.all { it.isHexDigit() }) {
            isError = false
            val color = Color(("ff$input").toLong(16))
            controller.selectByColor(color = color, fromUser = true)
        } else {
            isError = true
        }
    }

    if (isOpen) {
        AlertDialog(
            modifier = modifier.widthIn(max = 350.dp),
            onDismissRequest = onDismiss,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Selected Color")
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = controller.selectedColor.value,
                                shape = RoundedCornerShape(8.dp)
                            )
                    )
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    HsvColorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(ratio = 1f)
                            .padding(20.dp),
                        controller = controller,
                        onColorChanged = { colorEnvelope ->
                            hexInput = colorEnvelope.color.toColorHexString()
                            isError = false
                        }
                    )
                    BrightnessSlider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp),
                        controller = controller
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = hexInput,
                        onValueChange = { input ->
                            hexInput = input
                            handleHexInputChange(input)
                        },
                        label = { Text(text = "Enter Hex Color Code") },
                        prefix = { Text(text = "#") },
                        singleLine = true,
                        isError = isError,
                        supportingText = { Text(text = if (isError) "Invalid Hex Code" else "") }
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = "Close")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onColorSelected(controller.selectedColor.value)
                        onDismiss()
                    }
                ) {
                    Text(text = "Select")
                }
            }
        )
    }
}

private fun Color.toColorHexString() = String.format("%06X", 0xFFFFFF and this.toArgb())

private fun Char.isHexDigit(): Boolean {
    return this in '0'..'9' || this in 'A'..'F' || this in 'a'..'f'
}