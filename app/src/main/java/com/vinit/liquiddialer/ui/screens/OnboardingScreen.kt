package com.vinit.liquiddialer.ui.screens

import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingScreen(onComplete: () -> Unit) {
    val context = LocalContext.current
    var isDefaultDialer by remember { mutableStateOf(checkIsDefaultDialer(context)) }

    val roleLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        isDefaultDialer = checkIsDefaultDialer(context)
        if (isDefaultDialer) onComplete()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "LiquidDialer",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Free liquid-glass dialer\nNo ads • All themes unlocked",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(32.dp))

        if (!isDefaultDialer) {
            Button(
                onClick = {
                    requestDefaultDialer(context, roleLauncher)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Set as default dialer")
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Required for full call screen & T9 experience",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        } else {
            Text("Default dialer ✓", color = MaterialTheme.colorScheme.secondary)
            Spacer(Modifier.height(16.dp))
            Button(onClick = onComplete, modifier = Modifier.fillMaxWidth()) {
                Text("Continue")
            }
        }

        Spacer(Modifier.height(24.dp))
        TextButton(onClick = onComplete) {
            Text("Skip for now (limited features)")
        }
    }
}

private fun checkIsDefaultDialer(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val roleManager = context.getSystemService(RoleManager::class.java)
        roleManager?.isRoleHeld(RoleManager.ROLE_DIALER) == true
    } else {
        false
    }
}

private fun requestDefaultDialer(
    context: Context,
    launcher: androidx.activity.result.ActivityResultLauncher<Intent>
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val roleManager = context.getSystemService(RoleManager::class.java)
        if (roleManager != null && roleManager.isRoleAvailable(RoleManager.ROLE_DIALER)) {
            val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER)
            launcher.launch(intent)
            return
        }
    }
    // Fallback
    val intent = Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
    launcher.launch(intent)
}
