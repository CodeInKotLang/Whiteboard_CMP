package org.synac.whiteboard.domain.repository

import kotlinx.coroutines.flow.Flow
import org.synac.whiteboard.domain.model.Whiteboard

interface WhiteboardRepository {

    fun getAllWhiteboards(): Flow<List<Whiteboard>>

    suspend fun upsertWhiteboard(whiteboard: Whiteboard): Long

    suspend fun getWhiteboardById(id: Long): Whiteboard?
}