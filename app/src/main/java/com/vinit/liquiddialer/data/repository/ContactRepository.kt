package com.vinit.liquiddialer.data.repository

import android.content.Context
import android.provider.ContactsContract
import com.vinit.liquiddialer.domain.Contact
import com.vinit.liquiddialer.domain.T9Matcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun loadContacts(): List<Contact> = withContext(Dispatchers.IO) {
        val result = mutableListOf<Contact>()
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI
        )

        try {
            context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            )?.use { cursor ->
                val idIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
                val nameIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val numberIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val photoIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)

                val seen = mutableSetOf<String>()

                while (cursor.moveToNext()) {
                    val id = cursor.getString(idIdx) ?: continue
                    val name = cursor.getString(nameIdx) ?: continue
                    val number = cursor.getString(numberIdx)?.replace(" ", "")?.replace("-", "") ?: continue
                    val photo = cursor.getString(photoIdx)

                    val key = "$name|$number"
                    if (key in seen) continue
                    seen.add(key)

                    result.add(Contact(id = id, name = name, number = number, photoUri = photo))
                }
            }
        } catch (e: SecurityException) {
            // Permission not granted yet
        }
        result
    }

    fun filterT9(contacts: List<Contact>, query: String): List<Contact> {
        if (query.isBlank()) return emptyList()

        val isDigits = query.all { it.isDigit() || it == '*' || it == '#' }

        return if (isDigits) {
            contacts
                .map { it to T9Matcher.score(it.name, query) }
                .filter { it.second >= 0 }
                .sortedByDescending { it.second }
                .map { it.first }
                .take(12)
        } else {
            val q = query.lowercase()
            contacts.filter {
                it.name.lowercase().contains(q) || it.number.contains(q)
            }.take(12)
        }
    }
}
