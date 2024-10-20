package org.synac.whiteboard.domain.repository

import kotlinx.coroutines.flow.Flow
import org.synac.whiteboard.domain.model.ColorScheme

interface SettingsRepository {
    suspend fun saveColorScheme(colorScheme: ColorScheme)
    fun getColorScheme(): Flow<ColorScheme>
}