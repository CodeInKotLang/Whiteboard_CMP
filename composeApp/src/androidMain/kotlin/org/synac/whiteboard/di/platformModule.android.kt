package org.synac.whiteboard.di

import org.koin.dsl.module
import org.synac.whiteboard.data.local.getRoomDatabaseBuilder

actual val platformModule = module {
    single { getRoomDatabaseBuilder(get()) }
}