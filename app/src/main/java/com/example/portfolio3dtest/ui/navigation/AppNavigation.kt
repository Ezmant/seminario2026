package com.example.portfolio3dtest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.portfolio3dtest.ui.screens.DetailScreen
import com.example.portfolio3dtest.ui.screens.GalleryScreen
import com.example.portfolio3dtest.ui.screens.LoginScreen
import com.example.portfolio3dtest.ui.screens.ProfileScreen
import com.example.portfolio3dtest.ui.screens.RegisterScreen
import com.example.portfolio3dtest.ui.screens.SplashScreen
import com.example.portfolio3dtest.ui.screens.UploadScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Gallery : Screen("gallery")
    object Detail : Screen("detail/{projectId}") {
        fun createRoute(projectId: String) = "detail/$projectId"
    }
    object Upload : Screen("upload")
    object Profile : Screen("profile")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Gallery.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Gallery.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.Gallery.route) {
            GalleryScreen(
                onProjectClick = { projectId ->
                    navController.navigate(Screen.Detail.createRoute(projectId))
                },
                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                },
                onUploadClick = {
                    navController.navigate(Screen.Upload.route)
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: "1"
            DetailScreen(
                projectId = projectId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Upload.route) {
            UploadScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onUploadSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onProjectClick = { projectId ->
                    navController.navigate(Screen.Detail.createRoute(projectId))
                }
            )
        }
    }
}
