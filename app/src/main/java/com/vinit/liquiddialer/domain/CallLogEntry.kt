package com.vinit.liquiddialer.domain

data class CallLogEntry(
    val id: Long,
    val number: String,
    val name: String?,
    val type: Int,
    val timestamp: Long,
    val duration: Long,
    val isSpam: Boolean = false
)
