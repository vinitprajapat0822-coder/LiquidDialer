package com.vinit.liquiddialer.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vinit.liquiddialer.ui.screens.DialerScreen
import com.vinit.liquiddialer.ui.screens.OnboardingScreen

object Routes {
    const val ONBOARDING = "onboarding"
    const val DIALER = "dialer"
    const val RECENTS = "recents"
    const val CONTACTS = "contacts"
    const val SETTINGS = "settings"
}

@Composable
fun LiquidDialerNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.ONBOARDING
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
        composable(Routes.DIALER) {
            DialerScreen()
        }
    }
}
