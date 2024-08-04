package com.example.animation.commonutils

import com.google.gson.Gson

fun maskString(input: String): String {
    val length = input.length
    if (length < 4) {
        return "String is too short"
    }

    val numberOfGroups = (length - 4) / 4
    val remainder = (length - 4) % 4

    val firstPart = "**** ".repeat(numberOfGroups)
    val remainderPart = if (remainder > 0) "*".repeat(remainder) + " " else ""
    val lastPart = input.takeLast(4)

    return firstPart + remainderPart + lastPart
}

fun String?.valueOrDefault(default: String = ""): String = if (this !== null) this else default

fun <T> T.toJson(): String = Gson().toJson(this)