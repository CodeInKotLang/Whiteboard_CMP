package org.synac.whiteboard.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.synac.whiteboard.domain.repository.WhiteboardRepository

class DashboardViewModel(
    private val repository: WhiteboardRepository
): ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state = combine(
        _state,
        repository.getAllWhiteboards()
    ) { state, whiteboards ->
        state.copy(whiteboards = whiteboards)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = DashboardState()
    )
}