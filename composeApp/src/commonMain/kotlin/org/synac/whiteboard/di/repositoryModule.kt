package org.synac.whiteboard.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.synac.whiteboard.data.repository.PathRepositoryImpl
import org.synac.whiteboard.domain.repository.PathRepository

val repositoryModule = module {
    singleOf(::PathRepositoryImpl).bind<PathRepository>()
}