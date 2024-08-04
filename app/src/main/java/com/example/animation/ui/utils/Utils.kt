package com.example.animation.ui.utils

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.abs

fun getColorItem(index: Int): Brush {

    return when (index % 3) {
        0 -> Brush.linearGradient(
            colors = listOf(
                Color(0xFF2B2B2B),
                Color(0xFF4A4A4A),
            )
        )

        1 -> Brush.linearGradient(
            colors = listOf(
                Color(0xFF534AB3),
                Color(0xFF7D72EA),
            )
        )

        2 -> Brush.linearGradient(
            colors = listOf(
                Color(0xFF45968D),
                Color(0xFF67C9CA),
            )
        )

        else -> Brush.linearGradient(
            colors = listOf(
                Color(0xFF2B2B2B),
                Color(0xFF4A4A4A),
            )
        )
    }
}

fun isNegative(number: Double): Boolean {
    return number < 0
}

@SuppressLint("DefaultLocale")
fun removeNegativeSign(number: Double): String {
    val formattedNumber= abs(number)
    return String.format("%.2f", formattedNumber)
}