package com.vinit.liquiddialer.service

import android.content.Intent
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.speech.tts.TextToSpeech
import android.telecom.Call
import android.telecom.InCallService
import android.util.Log
import com.vinit.liquiddialer.CallActivity
import com.vinit.liquiddialer.data.Prefs
import java.util.Locale

class LiquidInCallService : InCallService() {
    private var tts: TextToSpeech? = null
    private var torchOn = false

    override fun onCallAdded(call: Call) {
        super.onCallAdded(call)
        currentCall = call
        val number = call.details?.handle?.schemeSpecificPart ?: ""
        val name = call.details?.callerDisplayName?.takeIf { it.isNotBlank() } ?: "Unknown"
        val prefs = Prefs(this)

        if (prefs.flashEnabled) startFlash()
        if (prefs.announcerEnabled) speak("$name calling")

        val intent = Intent(this, CallActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra(CallActivity.EXTRA_NAME, name)
            putExtra(CallActivity.EXTRA_NUMBER, number)
            putExtra(CallActivity.EXTRA_SPAM, prefs.isBlocked(number))
            putExtra(CallActivity.EXTRA_SLIDE, prefs.slideToAnswer)
        }
        startActivity(intent)
    }

    override fun onCallRemoved(call: Call) {
        super.onCallRemoved(call)
        if (currentCall == call) currentCall = null
        stopFlash()
        tts?.stop()
    }

    private fun startFlash() {
        try {
            val cm = getSystemService(CAMERA_SERVICE) as CameraManager
            val id = cm.cameraIdList.firstOrNull {
                cm.getCameraCharacteristics(it)
                    .get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
            } ?: return
            cm.setTorchMode(id, true)
            torchOn = true
            android.os.Handler(mainLooper).postDelayed({ stopFlash() }, 3000)
        } catch (e: Exception) {
            Log.w(TAG, "flash failed", e)
        }
    }

    private fun stopFlash() {
        if (!torchOn) return
        try {
            val cm = getSystemService(CAMERA_SERVICE) as CameraManager
            cm.cameraIdList.forEach { id ->
                try { cm.setTorchMode(id, false) } catch (_: Exception) {}
            }
        } catch (_: Exception) {}
        torchOn = false
    }

    private fun speak(text: String) {
        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.getDefault()
                tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "caller")
            }
        }
    }

    companion object {
        private const val TAG = "LiquidInCall"
        @Volatile var currentCall: Call? = null
    }
}
