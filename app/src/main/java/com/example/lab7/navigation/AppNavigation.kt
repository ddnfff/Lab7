package com.example.lab7.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab7.screens.WishDetailScreen
import com.example.lab7.screens.WishEditScreen
import com.example.lab7.screens.WishListScreen
import com.example.lab7.viewmodel.WishViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: WishViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            WishListScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = "detail/{wishId}",
            arguments = listOf(navArgument("wishId") { type = NavType.IntType })
        ) { backStackEntry ->
            WishDetailScreen(
                wishId = backStackEntry.arguments?.getInt("wishId") ?: 0,
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = "edit/{wishId}",
            arguments = listOf(navArgument("wishId") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) { backStackEntry ->
            WishEditScreen(
                wishId = backStackEntry.arguments?.getInt("wishId") ?: 0,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}