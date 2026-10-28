package com.vinit.liquiddialer.service

import android.content.Intent
import android.telecom.Call
import android.telecom.InCallService
import android.util.Log
import com.vinit.liquiddialer.CallActivity

class LiquidInCallService : InCallService() {

    override fun onCallAdded(call: Call) {
        super.onCallAdded(call)
        currentCall = call
        Log.d(TAG, "Call added: ${call.details?.handle}")

        val number = call.details?.handle?.schemeSpecificPart ?: ""
        val name = call.details?.callerDisplayName
            ?.takeIf { it.isNotBlank() }
            ?: "Unknown"

        val intent = Intent(this, CallActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra(CallActivity.EXTRA_NAME, name)
            putExtra(CallActivity.EXTRA_NUMBER, number)
            putExtra(CallActivity.EXTRA_SPAM, false)
        }
        startActivity(intent)

        call.registerCallback(object : Call.Callback() {
            override fun onStateChanged(call: Call, state: Int) {}
        })
    }

    override fun onCallRemoved(call: Call) {
        super.onCallRemoved(call)
        if (currentCall == call) currentCall = null
        Log.d(TAG, "Call removed")
    }

    companion object {
        private const val TAG = "LiquidInCall"
        @Volatile
        var currentCall: Call? = null
    }
}
