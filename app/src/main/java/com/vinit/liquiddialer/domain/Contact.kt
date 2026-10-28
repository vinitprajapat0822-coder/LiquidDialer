package com.vinit.liquiddialer.domain

data class Contact(
    val id: String,
    val name: String,
    val number: String,
    val photoUri: String? = null
)
