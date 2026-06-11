package edu.ucne.jorge_moya_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.jorge_moya_ap2_p1.presentation.edit.AmonestacionFormScreen
import edu.ucne.jorge_moya_ap2_p1.presentation.list.AmonestacionListScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ListScreen
    ) {
        composable<Screen.ListScreen> {
            AmonestacionListScreen(
                onNew = {
                    navController.navigate(Screen.FormScreen(0))
                },
                onNavigateToEdit = {id ->
                    navController.navigate(Screen.FormScreen(id))
                }
            )
        }

        composable<Screen.FormScreen> {
            AmonestacionFormScreen(
                onBack = {
                    navController.navigate(Screen.ListScreen) {
                        popUpTo(Screen.ListScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}