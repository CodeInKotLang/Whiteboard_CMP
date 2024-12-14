package org.synac.whiteboard.data.repository

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.synac.whiteboard.data.util.Constant.CANVAS_COLORS_PREF_KEY
import org.synac.whiteboard.data.util.Constant.COLOR_SCHEME_PREF_KEY
import org.synac.whiteboard.data.util.Constant.FILL_COLORS_PREF_KEY
import org.synac.whiteboard.data.util.Constant.STROKE_COLORS_PREF_KEY
import org.synac.whiteboard.domain.model.ColorPaletteType
import org.synac.whiteboard.domain.model.ColorScheme
import org.synac.whiteboard.domain.repository.SettingsRepository
import org.synac.whiteboard.presentation.theme.defaultCanvasColors
import org.synac.whiteboard.presentation.theme.defaultDrawingColors

class SettingRepositoryImpl(
    private val prefs: DataStore<Preferences>
): SettingsRepository {

    companion object {
        private val COLOR_SCHEME_KEY = stringPreferencesKey(COLOR_SCHEME_PREF_KEY)
        private val PREFERRED_STROKE_COLORS_KEY = stringPreferencesKey(STROKE_COLORS_PREF_KEY)
        private val PREFERRED_FILL_COLORS_KEY = stringPreferencesKey(FILL_COLORS_PREF_KEY)
        private val PREFERRED_CANVAS_COLORS_KEY = stringPreferencesKey(CANVAS_COLORS_PREF_KEY)
    }

    override suspend fun saveColorScheme(colorScheme: ColorScheme) {
        prefs.edit { preferences ->
            preferences[COLOR_SCHEME_KEY] = colorScheme.name
        }
    }

    override fun getColorScheme(): Flow<ColorScheme> {
        return prefs.data.map { preferences ->
            val scheme = preferences[COLOR_SCHEME_KEY] ?: ColorScheme.SYSTEM_DEFAULT.name
            ColorScheme.valueOf(scheme)
        }
    }

    override fun getPreferredStrokeColors(): Flow<List<Color>> {
        return prefs.data.map { preferences ->
            val colorsString = preferences[PREFERRED_STROKE_COLORS_KEY]
            colorsString?.parseColors() ?: defaultDrawingColors
        }
    }

    override fun getPreferredFillColors(): Flow<List<Color>> {
        return prefs.data.map { preferences ->
            val colorsString = preferences[PREFERRED_FILL_COLORS_KEY]
            colorsString?.parseColors() ?: defaultDrawingColors
        }
    }

    override fun getPreferredCanvasColors(): Flow<List<Color>> {
        return prefs.data.map { preferences ->
            val colorsString = preferences[PREFERRED_CANVAS_COLORS_KEY]
            colorsString?.parseColors() ?: defaultCanvasColors
        }
    }

    override suspend fun savePreferredColors(
        colors: List<Color>,
        colorPaletteType: ColorPaletteType
    ) {
        val colorsString = colors.map { it.toArgb() }.joinToString()
        val key = when(colorPaletteType) {
            ColorPaletteType.CANVAS -> PREFERRED_CANVAS_COLORS_KEY
            ColorPaletteType.STROKE -> PREFERRED_STROKE_COLORS_KEY
            ColorPaletteType.FILL -> PREFERRED_FILL_COLORS_KEY
        }
        prefs.edit { preference ->
            preference[key] = colorsString
        }
    }

    private fun String.parseColors() = this.split(", ").map { it.toInt() }.map { Color(it) }
}