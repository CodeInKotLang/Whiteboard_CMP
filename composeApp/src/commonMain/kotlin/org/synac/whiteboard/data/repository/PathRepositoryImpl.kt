package org.synac.whiteboard.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.synac.whiteboard.data.local.AppDatabase
import org.synac.whiteboard.data.mapper.toDrawnPaths
import org.synac.whiteboard.data.mapper.toPathEntity
import org.synac.whiteboard.domain.model.DrawnPath
import org.synac.whiteboard.domain.repository.PathRepository

class PathRepositoryImpl(
    private val database: AppDatabase
): PathRepository {

    private val dao = database.pathDao()

    override suspend fun upsertPath(path: DrawnPath) {
        dao.upsertPath(path.toPathEntity())
    }

    override suspend fun deletePath(path: DrawnPath) {
        dao.deletePath(path.toPathEntity())
    }

    override fun getAllPaths(): Flow<List<DrawnPath>> {
        return dao.getAllPaths().map { it.toDrawnPaths() }
    }
}