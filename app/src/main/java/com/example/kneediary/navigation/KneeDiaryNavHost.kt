package com.example.kneediary.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kneediary.ui.screens.navigated_screen.home_screen.HomeScreen
import com.example.kneediary.ui.screens.navigated_screen.home_screen.date_screen.DateScreen
import com.example.kneediary.ui.screens.start_screen.StartScreen

@Composable
fun KneeDiaryNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = Nav.StartScreen.name) {
                    composable(route = Nav.StartScreen.name) {
                        StartScreen(
                            onNavigateToHomeScreen = {
                                navController.navigate(Nav.HomeScreen.name)
                            },
                        )
                    }
                    composable(route = Nav.HomeScreen.name) {
                        HomeScreen()
                    }
                    composable(route = Nav.DateScreen.name) {
                        DateScreen()
                    }
                }
}