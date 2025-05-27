package br.unavet.appvetproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.unavet.appvetproject.ui.game.GameScreen
import br.unavet.appvetproject.ui.menu.MainMenuScreen
import br.unavet.appvetproject.ui.profile.ProfileScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = "game"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("main_menu") {
            MainMenuScreen(
                navigateToGame = {
                    navController.navigate("game")
                },
                navigateToProfile = {
                    navController.navigate("profile")
                }
            )
        }

        composable("game") {
            GameScreen(
                navigateToMainMenu = {
                    navController.navigate("main_menu") {
                        popUpTo("game") { inclusive = true }
                    }
                }
            )
        }

        composable("profile") {
            ProfileScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}