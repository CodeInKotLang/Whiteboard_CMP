package org.synac.whiteboard.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.synac.whiteboard.data.local.convertor.PathConverter
import org.synac.whiteboard.data.local.dao.PathDao
import org.synac.whiteboard.data.local.entity.PathEntity

@Database(
    entities = [PathEntity::class],
    version = 4,
    exportSchema = true
)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(PathConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pathDao(): PathDao
}