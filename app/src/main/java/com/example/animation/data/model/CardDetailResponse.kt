package com.example.animation.data.model


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.YearMonth

@Keep
data class CardDetailResponse(
    val cardSummary: CardSummary?,
    val transactions: List<CardTransaction>?
)

@Keep
data class CardSummary(
    val cardCategory: String?,
    val cardID: String?,
    val creditLimit: Int?,
    val currencyType: String?,
    val currentBalance: Double?
)

@Keep
data class CardTransaction(
    val amount: Double?,
    val date: String?,
    val merchant: String?,
    val status: String?,
    val transaction_id: String?,
    val type: String?,
    val dateAndTime: String?="",
    val year: String?="",
    val month: String?="",
)

@Keep
@Entity(tableName = "CardItem")
data class CardListItem(
    @PrimaryKey(autoGenerate = true)
    val uId:Int=0,
    val cardName: String,
    val cardNumber: String,
    val cardType: String?="",
    val cardExpireDate: String,
    val cardCategory: String?="",
    val holderName: String?="",
)
