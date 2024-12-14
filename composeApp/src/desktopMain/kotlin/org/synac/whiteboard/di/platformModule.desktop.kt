package org.synac.whiteboard.di

import org.koin.dsl.module
import org.synac.whiteboard.data.datastore.createDataStore
import org.synac.whiteboard.data.database.getRoomDatabaseBuilder
import org.synac.whiteboard.data.util.Constant.DATA_STORE_FILE_NAME

actual val platformModule = module {
    single { getRoomDatabaseBuilder() }
    single { createDataStore { DATA_STORE_FILE_NAME } }
}