package org.synac.whiteboard.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.synac.whiteboard.data.local.dao.PathDao
import org.synac.whiteboard.data.mapper.toDrawnPathList
import org.synac.whiteboard.data.mapper.toPathEntity
import org.synac.whiteboard.domain.model.DrawnPath
import org.synac.whiteboard.domain.repository.PathRepository

class PathRepositoryImpl(
    private val pathDao: PathDao
): PathRepository {

    override suspend fun upsertPath(path: DrawnPath) {
        pathDao.upsertPath(path.toPathEntity())
    }

    override suspend fun deletePath(path: DrawnPath) {
        pathDao.deletePath(path.toPathEntity())
    }

    override fun getPathsForWhiteboard(whiteboardId: Long): Flow<List<DrawnPath>> {
        return pathDao.getPathsForWhiteboard(whiteboardId).map { it.toDrawnPathList() }
    }
}