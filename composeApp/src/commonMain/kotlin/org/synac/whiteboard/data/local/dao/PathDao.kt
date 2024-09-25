package org.synac.whiteboard.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.synac.whiteboard.data.local.entity.PathEntity

@Dao
interface PathDao {

    @Upsert
    suspend fun upsertPath(pathEntity: PathEntity)

    @Delete
    suspend fun deletePath(pathEntity: PathEntity)

    @Query("SELECT * FROM path_table")
    fun getAllPaths(): Flow<List<PathEntity>>
}