package org.synac.whiteboard.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.synac.whiteboard.presentation.whiteboard.WhiteboardViewModel

val viewModelModule = module {
    viewModelOf(::WhiteboardViewModel)
}