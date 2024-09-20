package com.mahmoud.bugtracker.presentation.activities.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mahmoud.bugtracker.presentation.screens.MainScreen
import com.mahmoud.bugtracker.presentation.screens.SubmitBugScreen
import com.mahmoud.bugtracker.presentation.viewModels.BugViewModel
import com.mahmoud.bugtracker.utils.ScreensRoute

@Composable
fun MainScreenContent(
    navController: NavHostController,
    viewModel: BugViewModel,
    onSelectingImage: (() -> Unit)? = null
) {
    NavHost(navController = navController, startDestination = ScreensRoute.MAIN.name) {
        composable(ScreensRoute.MAIN.name) { MainScreen(navController) }
        composable(ScreensRoute.SUBMIT.name) {
            SubmitBugScreen(
                viewModel,
                onSelectingImage = {
                    onSelectingImage?.invoke()
                },
                onSubmit = { description ->
                    if (viewModel.imageUri.value != null) {
                        viewModel.uploadBugData(
                            description,
                            viewModel.imageUri.value.toString()
                        )
                    }
                }
            )
        }
    }
}
