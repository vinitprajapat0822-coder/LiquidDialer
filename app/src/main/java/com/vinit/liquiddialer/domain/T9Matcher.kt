package com.vinit.liquiddialer.domain

/**
 * Simple T9 matcher.
 * Maps keypad digits to letters and checks if a contact name matches the sequence.
 */
object T9Matcher {

    private val digitMap = mapOf(
        '2' to "ABC",
        '3' to "DEF",
        '4' to "GHI",
        '5' to "JKL",
        '6' to "MNO",
        '7' to "PQRS",
        '8' to "TUV",
        '9' to "WXYZ"
    )

    fun matches(name: String, digits: String): Boolean {
        if (digits.isEmpty()) return true
        val clean = name.uppercase().filter { it.isLetter() }
        if (clean.isEmpty()) return false
        return matchRecursive(clean, digits, 0, 0)
    }

    private fun matchRecursive(name: String, digits: String, nameIdx: Int, digIdx: Int): Boolean {
        if (digIdx == digits.length) return true
        if (nameIdx >= name.length) return false
        val d = digits[digIdx]
        val letters = digitMap[d] ?: return false
        for (i in nameIdx until name.length) {
            if (letters.contains(name[i])) {
                if (matchRecursive(name, digits, i + 1, digIdx + 1)) return true
            }
        }
        return false
    }

    fun score(name: String, digits: String): Int {
        if (!matches(name, digits)) return -1
        val clean = name.uppercase().filter { it.isLetter() }
        return 1000 - clean.length + digits.length * 10
    }
}
