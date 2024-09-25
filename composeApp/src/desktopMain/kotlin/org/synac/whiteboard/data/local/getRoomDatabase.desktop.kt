package org.synac.whiteboard.data.local

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.synac.whiteboard.data.util.Constant.APP_DATABASE_NAME
import java.io.File

fun getRoomDatabase(): AppDatabase {
    val dbFile = File(System.getProperty("java.io.tmpdir"), APP_DATABASE_NAME)
    return Room
        .databaseBuilder<AppDatabase>(name = dbFile.absolutePath)
        .fallbackToDestructiveMigration(dropAllTables = true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}