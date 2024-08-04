package com.example.animation.ui.utils

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.example.animation.commonutils.Constants
import com.example.animation.commonutils.getMonthOnly
import com.example.animation.commonutils.valueOrDefault
import com.example.animation.data.model.CardTransaction
import com.example.animation.ui.cardsdetail.ChartMapValue
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

fun getFilterData(
    cardTransactionItem: List<CardTransaction>?,
    selectChartType: String
): List<ChartMapValue>? {
    return when (selectChartType) {
        Constants.ChartType.DAY.value -> {
            cardTransactionItem?.groupBy { it.dateAndTime }
                ?.mapValues { entry -> entry.value.sumOf { it.amount ?: 0.00 } }?.map {
                    ChartMapValue(
                        date = it.key.valueOrDefault(),
                        total = it.value.toFloat()
                    )
                }
        }

        Constants.ChartType.MONTH.value -> {
            cardTransactionItem?.groupBy { it.month }
                ?.mapValues { entry -> entry.value.sumOf { it.amount ?: 0.00 } }?.map {
                    ChartMapValue(
                        date = getMonthOnly(it.key.valueOrDefault()),
                        total = it.value.toFloat()
                    )
                }
        }

        Constants.ChartType.YEARLY.value -> {
            cardTransactionItem?.groupBy { it.year }
                ?.mapValues { entry -> entry.value.sumOf { it.amount ?: 0.00 } }?.map {
                    ChartMapValue(
                        date = it.key.valueOrDefault(),
                        total = it.value.toFloat()
                    )
                }
        }

        else -> {
            null
        }
    }
}

fun getMaxChar(placeHolder: String): Int {
    return when (placeHolder) {
        Constants.PlaceholderType.NAME.value -> 20
        Constants.PlaceholderType.NUMBER.value -> 16
        Constants.PlaceholderType.EXPIRY.value -> 5
        Constants.PlaceholderType.CVV.value -> 3
        else -> 26
    }
}

fun getKeyboardType(placeHolder: String): KeyboardType {
    return when (placeHolder) {
        Constants.PlaceholderType.NAME.value -> KeyboardType.Text
        Constants.PlaceholderType.NUMBER.value -> KeyboardType.Phone
        Constants.PlaceholderType.CVV.value -> KeyboardType.Phone
        else -> KeyboardType.Text
    }
}