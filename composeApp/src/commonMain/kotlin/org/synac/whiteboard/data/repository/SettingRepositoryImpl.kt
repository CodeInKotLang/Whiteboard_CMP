package org.synac.whiteboard.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.synac.whiteboard.data.util.Constant.COLOR_SCHEME_PREF_KEY
import org.synac.whiteboard.domain.model.ColorScheme
import org.synac.whiteboard.domain.repository.SettingsRepository

class SettingRepositoryImpl(
    private val prefs: DataStore<Preferences>
): SettingsRepository {

    companion object {
        private val COLOR_SCHEME_KEY = stringPreferencesKey(COLOR_SCHEME_PREF_KEY)
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
}