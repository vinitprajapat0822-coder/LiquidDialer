package com.vinit.liquiddialer.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.History
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

object Routes {
    const val ONBOARDING = "onboarding"
    const val DIALER = "dialer"
    const val RECENTS = "recents"
}

@Composable
fun LiquidDialerNavHost() {
    val navController = rememberNavController()
    val backStack by navController.currentBackStackEntryAsState()
    val current = backStack?.destination?.route

    Scaffold(
        bottomBar = {
            if (current == Routes.DIALER || current == Routes.RECENTS) {
                NavigationBar {
                    NavigationBarItem(
                        selected = current == Routes.DIALER,
                        onClick = { navController.navigate(Routes.DIALER) { launchSingleTop = true } },
                        icon = { Icon(Icons.Default.Call, contentDescription = "Dialer") },
                        label = { Text("Dialer") }
                    )
                    NavigationBarItem(
                        selected = current == Routes.RECENTS,
                        onClick = { navController.navigate(Routes.RECENTS) { launchSingleTop = true } },
                        icon = { Icon(Icons.Default.History, contentDescription = "Recents") },
                        label = { Text("Recents") }
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
        }
    }
}
