package org.synac.whiteboard.presentation.dashboard

import org.synac.whiteboard.domain.model.Whiteboard

data class DashboardState(
    val whiteboards: List<Whiteboard> = emptyList()
)
