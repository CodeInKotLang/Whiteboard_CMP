package org.synac.whiteboard.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.synac.whiteboard.data.local.convertor.LocalDateConverter
import org.synac.whiteboard.data.local.convertor.PathConverter
import org.synac.whiteboard.data.local.dao.PathDao
import org.synac.whiteboard.data.local.dao.WhiteboardDao
import org.synac.whiteboard.data.local.entity.PathEntity
import org.synac.whiteboard.data.local.entity.WhiteboardEntity

@Database(
    version = 5,
    entities = [PathEntity::class, WhiteboardEntity::class],
    exportSchema = true
)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(PathConverter::class, LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pathDao(): PathDao
    abstract fun whiteboardDao(): WhiteboardDao
}