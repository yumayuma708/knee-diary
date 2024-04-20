package com.example.kneediary.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kneediary.ui.screens.navigated_screen.home_screen.HomeScreen
import com.example.kneediary.ui.screens.navigated_screen.home_screen.date_screen.DateScreen
import com.example.kneediary.ui.screens.navigated_screen.home_screen.note_screen.NoteScreen
import com.example.kneediary.ui.screens.navigated_screen.home_screen.weekly_screen.WeeklyScreen
import com.example.kneediary.ui.screens.record_screen.RecordNoteScreen
import com.example.kneediary.ui.screens.record_screen.record_screen.RecordScreen
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
            HomeScreen(
                navController = navController
            )
        }
        composable(route = Nav.DateScreen.name) {
            DateScreen()
        }
        composable(route = Nav.NoteScreen.name) {
            NoteScreen()
        }
        composable(route = Nav.WeeklyScreen.name) {
            WeeklyScreen()
        }
        composable(route = Nav.RecordScreen.name){
            RecordScreen(
                back = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Nav.RecordNoteScreen.name){
            RecordNoteScreen(
                navController = navController
            )
        }
    }
}