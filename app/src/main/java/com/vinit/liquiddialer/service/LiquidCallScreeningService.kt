package com.vinit.liquiddialer.service

import android.telecom.Call
import android.telecom.CallScreeningService
import android.util.Log

/**
 * Basic call screening. Later: check local block list + simple heuristics.
 */
class LiquidCallScreeningService : CallScreeningService() {

    override fun onScreenCall(callDetails: Call.Details) {
        val number = callDetails.handle?.schemeSpecificPart
        Log.d(TAG, "Screening: $number")

        val response = CallResponse.Builder()
            .setDisallowCall(false)
            .setRejectCall(false)
            .setSkipCallLog(false)
            .setSkipNotification(false)
            .build()
        respondToCall(callDetails, response)
    }

    companion object {
        private const val TAG = "LiquidScreening"
    }
}
