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
import org.synac.whiteboard.presentation.dashboard.DashboardScreen
import org.synac.whiteboard.presentation.settings.SettingsScreen
import org.synac.whiteboard.presentation.whiteboard.WhiteboardScreen
import org.synac.whiteboard.presentation.whiteboard.WhiteboardViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {

    NavHost(
        modifier = Modifier.padding(innerPadding),
        navController = navController,
        startDestination = Routes.DashboardScreen
    ) {
        composable<Routes.DashboardScreen> {
            DashboardScreen(
                onSettingsIconClick = { navController.navigate(Routes.SettingsScreen) },
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
                onBackIconClick = { navController.navigateUp() }
            )
        }
    }
}