package org.synac.whiteboard

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.synac.whiteboard.di.initKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@MyApplication)
        }
    }
}