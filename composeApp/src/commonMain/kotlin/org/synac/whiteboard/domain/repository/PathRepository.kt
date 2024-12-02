package org.synac.whiteboard.domain.repository

import kotlinx.coroutines.flow.Flow
import org.synac.whiteboard.domain.model.DrawnPath

interface PathRepository {
    suspend fun upsertPath(path: DrawnPath)
    suspend fun deletePath(path: DrawnPath)
    fun getPathsForWhiteboard(whiteboardId: Long): Flow<List<DrawnPath>>
}