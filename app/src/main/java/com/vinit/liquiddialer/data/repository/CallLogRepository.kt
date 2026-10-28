package com.vinit.liquiddialer.data.repository

import android.content.Context
import android.provider.CallLog
import com.vinit.liquiddialer.domain.CallLogEntry
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CallLogRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun loadRecent(limit: Int = 100): List<CallLogEntry> = withContext(Dispatchers.IO) {
        val result = mutableListOf<CallLogEntry>()
        val projection = arrayOf(
            CallLog.Calls._ID,
            CallLog.Calls.NUMBER,
            CallLog.Calls.CACHED_NAME,
            CallLog.Calls.TYPE,
            CallLog.Calls.DATE,
            CallLog.Calls.DURATION
        )
        try {
            context.contentResolver.query(
                CallLog.Calls.CONTENT_URI,
                projection,
                null,
                null,
                CallLog.Calls.DATE + " DESC"
            )?.use { c ->
                val idIdx = c.getColumnIndex(CallLog.Calls._ID)
                val numIdx = c.getColumnIndex(CallLog.Calls.NUMBER)
                val nameIdx = c.getColumnIndex(CallLog.Calls.CACHED_NAME)
                val typeIdx = c.getColumnIndex(CallLog.Calls.TYPE)
                val dateIdx = c.getColumnIndex(CallLog.Calls.DATE)
                val durIdx = c.getColumnIndex(CallLog.Calls.DURATION)
                var count = 0
                while (c.moveToNext() && count < limit) {
                    result.add(
                        CallLogEntry(
                            id = c.getLong(idIdx),
                            number = c.getString(numIdx) ?: "",
                            name = c.getString(nameIdx),
                            type = c.getInt(typeIdx),
                            timestamp = c.getLong(dateIdx),
                            duration = c.getLong(durIdx)
                        )
                    )
                    count++
                }
            }
        } catch (_: SecurityException) {}
        result
    }
}
