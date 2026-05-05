package com.example.lab7.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab7.screens.PersonDetailScreen
import com.example.lab7.screens.PersonEditScreen
import com.example.lab7.screens.PersonListScreen
import com.example.lab7.viewmodel.PersonViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lab7.screens.UserTodosScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: PersonViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            PersonListScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = "todos/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            UserTodosScreen(
                userId = backStackEntry.arguments?.getInt("userId") ?: 0,
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = "detail/{personId}",
            arguments = listOf(navArgument("personId") { type = NavType.IntType })
        ) { backStackEntry ->
            PersonDetailScreen(
                personId = backStackEntry.arguments?.getInt("personId") ?: 0,
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = "edit/{personId}",
            arguments = listOf(navArgument("personId") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) { backStackEntry ->
            PersonEditScreen(
                personId = backStackEntry.arguments?.getInt("personId") ?: 0,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}