package org.synac.whiteboard.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.synac.whiteboard.domain.model.ColorScheme
import org.synac.whiteboard.presentation.dashboard.DashboardScreen
import org.synac.whiteboard.presentation.dashboard.DashboardState
import org.synac.whiteboard.presentation.dashboard.DashboardViewModel
import org.synac.whiteboard.presentation.settings.SettingsScreen
import org.synac.whiteboard.presentation.whiteboard.WhiteboardScreen
import org.synac.whiteboard.presentation.whiteboard.WhiteboardViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    currentScheme: ColorScheme,
    onThemeSelected: (ColorScheme) -> Unit
) {

    NavHost(
        modifier = Modifier.padding(innerPadding),
        navController = navController,
        startDestination = Routes.DashboardScreen
    ) {
        composable<Routes.DashboardScreen> {
            val viewModel = koinViewModel<DashboardViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            DashboardScreen(
                state = state,
                onSettingsIconClick = { navController.navigate(Routes.SettingsScreen) },
                onCardClick = { whiteboardId ->
                    navController.navigate(Routes.WhiteboardScreen(whiteboardId = whiteboardId))
                },
                onAddNewWhiteboardClick = {
                    navController.navigate(Routes.WhiteboardScreen(whiteboardId = null))
                }
            )
        }
        composable<Routes.WhiteboardScreen> {
            val viewModel = koinViewModel<WhiteboardViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            WhiteboardScreen(
                state = state,
                onEvent = viewModel::onEvent,
                onHomeIconClick = { navController.navigateUp() }
            )
        }
        composable<Routes.SettingsScreen> {
            SettingsScreen(
                currentScheme = currentScheme,
                onThemeSelected = onThemeSelected,
                onBackIconClick = { navController.navigateUp() }
            )
        }
    }
}