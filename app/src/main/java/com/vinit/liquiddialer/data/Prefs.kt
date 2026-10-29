package com.vinit.liquiddialer.data

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val p: SharedPreferences =
        context.getSharedPreferences("liquiddialer", Context.MODE_PRIVATE)

    var flashEnabled: Boolean
        get() = p.getBoolean("flash", false)
        set(v) = p.edit().putBoolean("flash", v).apply()

    var announcerEnabled: Boolean
        get() = p.getBoolean("announcer", false)
        set(v) = p.edit().putBoolean("announcer", v).apply()

    var slideToAnswer: Boolean
        get() = p.getBoolean("slide", true)
        set(v) = p.edit().putBoolean("slide", v).apply()

    var themeId: String
        get() = p.getString("theme", "glass_dark") ?: "glass_dark"
        set(v) = p.edit().putString("theme", v).apply()

    fun blockedNumbers(): MutableSet<String> =
        p.getStringSet("blocked", emptySet())?.toMutableSet() ?: mutableSetOf()

    fun block(number: String) {
        val s = blockedNumbers()
        s.add(number.replace(" ", "").replace("-", ""))
        p.edit().putStringSet("blocked", s).apply()
    }

    fun unblock(number: String) {
        val s = blockedNumbers()
        s.remove(number.replace(" ", "").replace("-", ""))
        p.edit().putStringSet("blocked", s).apply()
    }

    fun isBlocked(number: String): Boolean {
        val n = number.replace(" ", "").replace("-", "")
        return blockedNumbers().any { n.endsWith(it) || it.endsWith(n) }
    }
}
