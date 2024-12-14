package org.synac.whiteboard.di

import org.koin.dsl.module
import org.synac.whiteboard.data.datastore.dataStore
import org.synac.whiteboard.data.database.getRoomDatabaseBuilder

actual val platformModule = module {
    single { getRoomDatabaseBuilder(get()) }
    single { dataStore(get()) }
}