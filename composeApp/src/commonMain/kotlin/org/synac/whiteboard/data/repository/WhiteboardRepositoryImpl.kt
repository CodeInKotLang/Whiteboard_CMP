package org.synac.whiteboard.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.synac.whiteboard.data.local.dao.WhiteboardDao
import org.synac.whiteboard.data.mapper.toWhiteboard
import org.synac.whiteboard.data.mapper.toWhiteboardEntity
import org.synac.whiteboard.data.mapper.toWhiteboardList
import org.synac.whiteboard.domain.model.Whiteboard
import org.synac.whiteboard.domain.repository.WhiteboardRepository

class WhiteboardRepositoryImpl(
    private val whiteboardDao: WhiteboardDao
): WhiteboardRepository {

    override fun getAllWhiteboards(): Flow<List<Whiteboard>> {
        return whiteboardDao.getAllWhiteboards().map { it.toWhiteboardList() }
    }

    override suspend fun upsertWhiteboard(whiteboard: Whiteboard): Long {
        return if (whiteboard.id == null) {
            whiteboardDao.insertWhiteboard(whiteboard.toWhiteboardEntity())
        } else {
            whiteboardDao.updateWhiteboard(whiteboard.toWhiteboardEntity())
            whiteboard.id
        }
    }

    override suspend fun getWhiteboardById(id: Long): Whiteboard? {
        return whiteboardDao.getWhiteboardById(id)?.toWhiteboard()
    }
}