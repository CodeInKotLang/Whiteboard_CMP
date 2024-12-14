package org.synac.whiteboard.domain.repository

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.Flow
import org.synac.whiteboard.domain.model.ColorPaletteType
import org.synac.whiteboard.domain.model.ColorScheme

interface SettingsRepository {
    fun getColorScheme(): Flow<ColorScheme>
    fun getPreferredStrokeColors(): Flow<List<Color>>
    fun getPreferredFillColors(): Flow<List<Color>>
    fun getPreferredCanvasColors(): Flow<List<Color>>
    suspend fun saveColorScheme(colorScheme: ColorScheme)
    suspend fun savePreferredColors(colors: List<Color>, colorPaletteType: ColorPaletteType)
}