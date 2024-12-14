package org.synac.whiteboard.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.synac.whiteboard.data.database.entity.PathEntity

@Dao
interface PathDao {

    @Upsert
    suspend fun upsertPath(pathEntity: PathEntity)

    @Delete
    suspend fun deletePath(pathEntity: PathEntity)

    @Query("SELECT * FROM path_table WHERE whiteboardId = :whiteboardId")
    fun getPathsForWhiteboard(whiteboardId: Long): Flow<List<PathEntity>>
}