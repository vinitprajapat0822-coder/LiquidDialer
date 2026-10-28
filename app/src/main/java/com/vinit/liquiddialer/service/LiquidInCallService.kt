package com.vinit.liquiddialer.service

import android.telecom.Call
import android.telecom.InCallService
import android.util.Log

/**
 * Minimal InCallService for vertical slice.
 * Full glass UI + slide-to-answer will be added in next iteration.
 */
class LiquidInCallService : InCallService() {

    override fun onCallAdded(call: Call) {
        super.onCallAdded(call)
        Log.d(TAG, "Call added: ${call.details?.handle}")
        // TODO: Launch full-screen glass CallActivity / Compose UI
    }

    override fun onCallRemoved(call: Call) {
        super.onCallRemoved(call)
        Log.d(TAG, "Call removed")
    }

    companion object {
        private const val TAG = "LiquidInCall"
    }
}
