package com.vinit.liquiddialer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.vinit.liquiddialer.ui.LiquidDialerNavHost
import com.vinit.liquiddialer.ui.theme.LiquidDialerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiquidDialerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LiquidDialerNavHost()
                }
            }
        }
    }
}
