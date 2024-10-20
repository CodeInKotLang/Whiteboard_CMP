package org.synac.whiteboard.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.synac.whiteboard.domain.model.ColorScheme
import org.synac.whiteboard.domain.repository.SettingsRepository

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
): ViewModel() {

    val currentColorScheme: StateFlow<ColorScheme> = settingsRepository.getColorScheme()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = ColorScheme.SYSTEM_DEFAULT
        )

    fun saveColorScheme(colorScheme: ColorScheme) {
        viewModelScope.launch {
            settingsRepository.saveColorScheme(colorScheme)
        }
    }
}