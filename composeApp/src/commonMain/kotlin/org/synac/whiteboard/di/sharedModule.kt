package org.synac.whiteboard.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.synac.whiteboard.data.local.AppDatabase
import org.synac.whiteboard.data.local.getRoomDatabase
import org.synac.whiteboard.presentation.whiteboard.WhiteboardViewModel
import org.synac.whiteboard.presentation.settings.SettingsViewModel
import org.synac.whiteboard.data.repository.PathRepositoryImpl
import org.synac.whiteboard.data.repository.SettingRepositoryImpl
import org.synac.whiteboard.domain.repository.PathRepository
import org.synac.whiteboard.domain.repository.SettingsRepository

val sharedModule = module {

    //Database
    single { getRoomDatabase(get()) }
    single { get<AppDatabase>().pathDao() }

    //ViewModels
    viewModelOf(::WhiteboardViewModel)
    viewModelOf(::SettingsViewModel)

    //Repositories
    singleOf(::PathRepositoryImpl).bind<PathRepository>()
    singleOf(::SettingRepositoryImpl).bind<SettingsRepository>()
}