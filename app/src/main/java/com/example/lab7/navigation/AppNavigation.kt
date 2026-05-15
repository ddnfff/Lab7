package com.example.lab7.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab7.screens.ServiceDetailScreen
import com.example.lab7.screens.ServiceEditScreen
import com.example.lab7.screens.ServiceListScreen
import com.example.lab7.viewmodel.ServiceViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: ServiceViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            ServiceListScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = "detail/{recordId}",
            arguments = listOf(navArgument("recordId") { type = NavType.IntType })
        ) { backStackEntry ->
            ServiceDetailScreen(
                recordId = backStackEntry.arguments?.getInt("recordId") ?: 0,
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = "edit/{recordId}",
            arguments = listOf(navArgument("recordId") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) { backStackEntry ->
            ServiceEditScreen(
                recordId = backStackEntry.arguments?.getInt("recordId") ?: 0,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}