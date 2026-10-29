package com.vinit.liquiddialer.service

import android.telecom.Call
import android.telecom.CallScreeningService
import android.util.Log
import com.vinit.liquiddialer.data.Prefs

class LiquidCallScreeningService : CallScreeningService() {
    override fun onScreenCall(callDetails: Call.Details) {
        val number = callDetails.handle?.schemeSpecificPart ?: ""
        val prefs = Prefs(this)
        val blocked = prefs.isBlocked(number)
        Log.d(TAG, "Screen $number blocked=$blocked")

        val response = CallResponse.Builder()
            .setDisallowCall(blocked)
            .setRejectCall(blocked)
            .setSkipCallLog(false)
            .setSkipNotification(blocked)
            .build()
        respondToCall(callDetails, response)
    }
    companion object { private const val TAG = "LiquidScreening" }
}
