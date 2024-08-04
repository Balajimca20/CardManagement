package com.example.animation.commonutils

object Constants {

    enum class CardType(val value: String) {
        VISA("visa"),
        MASTERCARD("mastercard"),
    }

    enum class TransactionType(val value: String) {
        DEBIT("debit"),
        CREDIT("credit"),
    }

    enum class ChartType(val value: String) {
        DAY("Day"),
        MONTH("Month"),
        YEARLY("Yearly"),
    }

    enum class PlaceholderType(val value: String) {
        NAME("name"),
        NUMBER("**** **** **** ****"),
        EXPIRY("MM/YY"),
        CVV("CVV"),
    }
}