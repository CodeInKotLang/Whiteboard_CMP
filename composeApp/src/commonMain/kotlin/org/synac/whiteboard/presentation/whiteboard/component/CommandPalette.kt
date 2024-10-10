package org.synac.whiteboard.presentation.whiteboard.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.synac.whiteboard.domain.model.DrawingTool
import whiteboard.composeapp.generated.resources.Res
import whiteboard.composeapp.generated.resources.ic_transparent_bg
import whiteboard.composeapp.generated.resources.img_color_wheel

@Composable
fun CommandPaletteDrawerContent(
    modifier: Modifier = Modifier,
    selectedDrawingTool: DrawingTool,
    onCloseIconClick: () -> Unit,
    strokeColors: List<Color>,
    selectedStrokeColor: Color,
    onStrokeColorChange: (Color) -> Unit,
    backgroundColors: List<Color>,
    selectedBackgroundColor: Color,
    onBackgroundColorChange: (Color) -> Unit,
    strokeSliderValue: Float,
    onStrokeSliderValueChange: (Float) -> Unit,
    opacitySliderValue: Float,
    onOpacitySliderValueChange: (Float) -> Unit,
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight()
    ) {
        CommandPaletteContent(
            selectedDrawingTool = selectedDrawingTool,
            onCloseIconClick = onCloseIconClick,
            strokeColors = strokeColors,
            selectedStrokeColor = selectedStrokeColor,
            onStrokeColorChange = onStrokeColorChange,
            backgroundColors = backgroundColors,
            selectedBackgroundColor = selectedBackgroundColor,
            onBackgroundColorChange = onBackgroundColorChange,
            strokeSliderValue = strokeSliderValue,
            onStrokeSliderValueChange = onStrokeSliderValueChange,
            opacitySliderValue = opacitySliderValue,
            onOpacitySliderValueChange = onOpacitySliderValueChange
        )
    }
}

@Composable
fun CommandPaletteCard(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    selectedDrawingTool: DrawingTool,
    onCloseIconClick: () -> Unit,
    strokeColors: List<Color>,
    selectedStrokeColor: Color,
    onStrokeColorChange: (Color) -> Unit,
    backgroundColors: List<Color>,
    selectedBackgroundColor: Color,
    onBackgroundColorChange: (Color) -> Unit,
    strokeSliderValue: Float,
    onStrokeSliderValueChange: (Float) -> Unit,
    opacitySliderValue: Float,
    onOpacitySliderValueChange: (Float) -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        ElevatedCard(
            modifier = modifier.width(250.dp)
        ) {
            CommandPaletteContent(
                selectedDrawingTool = selectedDrawingTool,
                onCloseIconClick = onCloseIconClick,
                strokeColors = strokeColors,
                selectedStrokeColor = selectedStrokeColor,
                onStrokeColorChange = onStrokeColorChange,
                backgroundColors = backgroundColors,
                selectedBackgroundColor = selectedBackgroundColor,
                onBackgroundColorChange = onBackgroundColorChange,
                strokeSliderValue = strokeSliderValue,
                onStrokeSliderValueChange = onStrokeSliderValueChange,
                opacitySliderValue = opacitySliderValue,
                onOpacitySliderValueChange = onOpacitySliderValueChange
            )
        }
    }
}

@Composable
private fun CommandPaletteContent(
    modifier: Modifier = Modifier,
    selectedDrawingTool: DrawingTool,
    onCloseIconClick: () -> Unit,
    strokeColors: List<Color>,
    selectedStrokeColor: Color,
    onStrokeColorChange: (Color) -> Unit,
    backgroundColors: List<Color>,
    selectedBackgroundColor: Color,
    onBackgroundColorChange: (Color) -> Unit,
    strokeSliderValue: Float,
    onStrokeSliderValueChange: (Float) -> Unit,
    opacitySliderValue: Float,
    onOpacitySliderValueChange: (Float) -> Unit,
) {
    val updatedStrokeColors = listOf(Color.Black) + strokeColors

    Column(
        modifier = modifier.padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Untitled",
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = onCloseIconClick) {
                Icon(Icons.Default.Close, contentDescription = "Close Command Palette")
            }
        }
        HorizontalDivider(modifier = Modifier.height(20.dp))
        ColorSection(
            sectionTitle = "Stroke",
            colors = updatedStrokeColors,
            selectedColor = selectedStrokeColor,
            onColorChange = onStrokeColorChange,
            onColorPaletteClick = {}
        )
        when(selectedDrawingTool) {
            DrawingTool.RECTANGLE, DrawingTool.CIRCLE, DrawingTool.TRIANGLE -> {
                Spacer(modifier = Modifier.height(20.dp))
                ColorSection(
                    sectionTitle = "Background",
                    colors = backgroundColors,
                    isBackgroundColors = true,
                    selectedColor = selectedBackgroundColor,
                    onColorChange = onBackgroundColorChange,
                    onColorPaletteClick = {}
                )
            }
            else -> Unit
        }
        Spacer(modifier = Modifier.height(20.dp))
        SliderSection(
            sectionTitle = "Stroke Width",
            sliderValueRange = 1f..25f,
            sliderValue = strokeSliderValue,
            onSliderValueChange =  onStrokeSliderValueChange
        )
        Spacer(modifier = Modifier.height(15.dp))
        SliderSection(
            sectionTitle = "Opacity",
            sliderValueRange = 1f..100f,
            sliderValue = opacitySliderValue,
            onSliderValueChange =  onOpacitySliderValueChange
        )
    }
}

@Composable
private fun ColorSection(
    sectionTitle: String,
    isBackgroundColors: Boolean = false,
    colors: List<Color>,
    selectedColor: Color,
    onColorChange: (Color) -> Unit,
    onColorPaletteClick: () -> Unit
) {
    Column {
        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isBackgroundColors) {
                item {
                    Icon(
                        modifier = Modifier
                            .size(30.dp)
                            .border(
                                width = 1.dp,
                                color = if (selectedColor == Color.Transparent) {
                                    MaterialTheme.colorScheme.primary
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .padding(2.dp)
                            .clip(CircleShape)
                            .clickable { onColorChange(Color.Transparent) },
                        painter = painterResource(Res.drawable.ic_transparent_bg),
                        contentDescription = "Set transparent background",
                        tint = Color.Unspecified
                    )
                }
            }
            items(colors) { color ->
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .border(
                            width = 1.dp,
                            color = if (selectedColor == color) {
                                MaterialTheme.colorScheme.primary
                            } else Color.Transparent,
                            shape = CircleShape
                        )
                        .padding(2.dp)
                        .background(color, CircleShape)
                        .clickable { onColorChange(color) }
                )
            }
            item {
                Spacer(modifier = Modifier.width(5.dp))
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = { onColorPaletteClick() }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.img_color_wheel),
                        contentDescription = "Set custom color",
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@Composable
private fun SliderSection(
    modifier: Modifier = Modifier,
    sectionTitle: String,
    sliderValue: Float,
    onSliderValueChange: (Float) -> Unit,
    sliderValueRange: ClosedFloatingPointRange<Float>
) {
    Column(modifier = modifier) {
        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.titleSmall
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Slider(
                modifier = Modifier.weight(1f),
                value = sliderValue,
                onValueChange = onSliderValueChange,
                valueRange = sliderValueRange
            )
            Text(text = "${sliderValue.toInt()}")
        }
    }
}







