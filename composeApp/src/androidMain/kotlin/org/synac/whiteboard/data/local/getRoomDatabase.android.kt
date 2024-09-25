package org.synac.whiteboard.data.local

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.synac.whiteboard.data.util.Constant.APP_DATABASE_NAME

fun getRoomDatabase(context: Context): AppDatabase {
    val dbFile = context.getDatabasePath(APP_DATABASE_NAME)
    return Room
        .databaseBuilder<AppDatabase>(context = context, name = dbFile.absolutePath)
        .fallbackToDestructiveMigration(dropAllTables = true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}