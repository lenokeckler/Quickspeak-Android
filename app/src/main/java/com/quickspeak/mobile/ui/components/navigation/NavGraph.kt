package com.quickspeak.mobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.*
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import com.quickspeak.mobile.ui.login.LoginScreen
import com.quickspeak.mobile.ui.login.SignUpScreen
import com.quickspeak.mobile.ui.components.navigation.NavigationDrawerContent
import com.quickspeak.mobile.ui.screens.test.AvatarScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickSpeakNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onSignUpClick = { navController.navigate("signup") },
                onLoginClick = { navController.navigate("main") }
            )
        }

        composable("signup") {
            SignUpScreen(
                onLoginClicked = { navController.popBackStack() }
            )
        }

        composable("signup") {
            SignUpScreen(
                onLoginClicked = { navController.popBackStack() },
                onBackClicked = { navController.popBackStack() }
            )
        }


        composable("main") {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    NavigationDrawerContent(
                        onLogout = {
                            navController.navigate("login") {
                                popUpTo("main") { inclusive = true }
                            }
                        }
                    )
                },
                content = {
                    AvatarScreen(onMenuClick = { scope.launch { drawerState.open() } })
                }
            )
        }
    }
}
