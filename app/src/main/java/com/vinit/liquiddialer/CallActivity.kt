package com.vinit.liquiddialer

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vinit.liquiddialer.service.LiquidInCallService
import com.vinit.liquiddialer.ui.screens.CallScreen
import com.vinit.liquiddialer.ui.theme.LiquidDialerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CallActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        )
        enableEdgeToEdge()
        val name = intent.getStringExtra(EXTRA_NAME) ?: "Unknown"
        val number = intent.getStringExtra(EXTRA_NUMBER) ?: ""
        val isSpam = intent.getBooleanExtra(EXTRA_SPAM, false)
        val showSlide = intent.getBooleanExtra(EXTRA_SLIDE, true)

        setContent {
            LiquidDialerTheme(darkTheme = true) {
                CallScreen(
                    callerName = name,
                    callerNumber = number,
                    isSpam = isSpam,
                    showSlide = showSlide,
                    onAnswer = {
                        LiquidInCallService.currentCall?.answer(0)
                        finish()
                    },
                    onReject = {
                        LiquidInCallService.currentCall?.reject(false, null)
                        finish()
                    },
                    onMessage = { finish() },
                    onRemind = { finish() }
                )
            }
        }
    }
    companion object {
        const val EXTRA_NAME = "caller_name"
        const val EXTRA_NUMBER = "caller_number"
        const val EXTRA_SPAM = "is_spam"
        const val EXTRA_SLIDE = "show_slide"
    }
}
