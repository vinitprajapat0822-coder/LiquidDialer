package com.vinit.liquiddialer.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vinit.liquiddialer.ui.screens.DialerScreen
import com.vinit.liquiddialer.ui.screens.OnboardingScreen
import com.vinit.liquiddialer.ui.screens.RecentsScreen
import com.vinit.liquiddialer.ui.screens.SettingsScreen

object Routes {
    const val ONBOARDING = "onboarding"
    const val DIALER = "dialer"
    const val RECENTS = "recents"
    const val SETTINGS = "settings"
}

@Composable
fun LiquidDialerNavHost() {
    val navController = rememberNavController()
    val backStack by navController.currentBackStackEntryAsState()
    val current = backStack?.destination?.route
    val showBar = current in setOf(Routes.DIALER, Routes.RECENTS, Routes.SETTINGS)

    Scaffold(
        bottomBar = {
            if (showBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = current == Routes.DIALER,
                        onClick = {
                            navController.navigate(Routes.DIALER) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Call, "Dialer") },
                        label = { Text("Dialer") }
                    )
                    NavigationBarItem(
                        selected = current == Routes.RECENTS,
                        onClick = {
                            navController.navigate(Routes.RECENTS) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.History, "Recents") },
                        label = { Text("Recents") }
                    )
                    NavigationBarItem(
                        selected = current == Routes.SETTINGS,
                        onClick = {
                            navController.navigate(Routes.SETTINGS) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Settings, "Settings") },
                        label = { Text("Settings") }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.ONBOARDING,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.ONBOARDING) {
                OnboardingScreen(
                    onComplete = {
                        navController.navigate(Routes.DIALER) {
                            popUpTo(Routes.ONBOARDING) { inclusive = true }
                        }
                    }
                )
            }
            composable(Routes.DIALER) { DialerScreen() }
            composable(Routes.RECENTS) { RecentsScreen() }
            composable(Routes.SETTINGS) { SettingsScreen() }
        }
    }
}
