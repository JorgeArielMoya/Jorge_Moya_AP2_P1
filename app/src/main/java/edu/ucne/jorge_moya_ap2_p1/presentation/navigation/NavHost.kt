package edu.ucne.jorge_moya_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.jorge_moya_ap2_p1.presentation.edit.FormScreen
import edu.ucne.jorge_moya_ap2_p1.presentation.list.ListScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ListScreen
    ) {
        composable<Screen.ListScreen> {
            ListScreen(
                onNew = {
                    navController.navigate(Screen.FormScreen(0))
                }
            )
        }

        composable<Screen.FormScreen> {
            FormScreen(
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