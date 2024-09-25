package org.synac.whiteboard.di

import org.koin.dsl.module
import org.synac.whiteboard.data.local.getRoomDatabase

actual fun platformModule() = module {
    single { getRoomDatabase() }
}