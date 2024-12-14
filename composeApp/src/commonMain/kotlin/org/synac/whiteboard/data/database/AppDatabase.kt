package org.synac.whiteboard.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.synac.whiteboard.data.database.convertor.LocalDateConverter
import org.synac.whiteboard.data.database.convertor.PathConverter
import org.synac.whiteboard.data.database.dao.PathDao
import org.synac.whiteboard.data.database.dao.WhiteboardDao
import org.synac.whiteboard.data.database.entity.PathEntity
import org.synac.whiteboard.data.database.entity.WhiteboardEntity

@Database(
    version = 6,
    entities = [PathEntity::class, WhiteboardEntity::class],
    exportSchema = true
)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(PathConverter::class, LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pathDao(): PathDao
    abstract fun whiteboardDao(): WhiteboardDao
}