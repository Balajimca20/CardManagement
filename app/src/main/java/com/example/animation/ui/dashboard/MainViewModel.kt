package com.example.animation.ui.dashboard

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animation.apiutils.Resource
import com.example.animation.commonutils.Constants
import com.example.animation.commonutils.getDateOnly
import com.example.animation.commonutils.getMonthAndYear
import com.example.animation.commonutils.getYearOnly
import com.example.animation.commonutils.toJson
import com.example.animation.commonutils.valueOrDefault
import com.example.animation.data.model.CardDetailResponse
import com.example.animation.data.model.CardListItem
import com.example.animation.data.model.CardTransaction
import com.example.animation.data.repository.CardDetailsRepository
import com.example.animation.ui.addcard.CreateCardEvents
import com.example.animation.ui.addcard.PopupFilterModel
import com.example.animation.ui.navigation.FrequentlyItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: CardDetailsRepository,
) : ViewModel() {


    private var cardItem = arrayListOf(
        CardListItem(
            cardName = "Dutch Bangla Bank",
            cardNumber = "1234567890123456",
            cardType = "Platinum Plus",
            cardExpireDate = "Exp 01/22",
            cardCategory = "VISA",
            holderName = "Sunny Aveiro"
        ),
        CardListItem(
            cardName = "Dutch Bangla Bank",
            cardNumber = "1234567890123456",
            cardType = "Platinum Plus",
            cardExpireDate = "Exp 01/22",
            cardCategory = "VISA",
            holderName = "Sunny Aveiro"
        ),
        CardListItem(
            cardName = "Dutch Bangla Bank",
            cardNumber = "1234567890123456",
            cardType = "Platinum Plus",
            cardExpireDate = "Exp 01/22",
            cardCategory = "VISA",
            holderName = "Sunny Aveiro"
        )
    )
    private val frequentlyItem = listOf(
        FrequentlyItem.MobileRecharge,
        FrequentlyItem.BillPayment,
        FrequentlyItem.BankTransfer,
        FrequentlyItem.RequestMoney,
        FrequentlyItem.TransferHistory,
    )
    private val serviceItem = listOf(
        FrequentlyItem.OpenAccount,
        FrequentlyItem.ManageCards,
    )
    private val cardTransactionItem = listOf(
        CardTransaction(
            transaction_id = "txn001",
            date = "2024-07-01T10:15:30Z",
            merchant = "SuperMart",
            status = "success",
            type = "debit",
            amount = -45.67
        ),
        CardTransaction(
            transaction_id = "txn001",
            date = "2024-07-01T10:15:30Z",
            merchant = "SuperMart",
            status = "success",
            type = "debit",
            amount = -45.67
        ),
        CardTransaction(
            transaction_id = "txn001",
            date = "2024-07-01T10:15:30Z",
            merchant = "SuperMart",
            status = "success",
            type = "debit",
            amount = -45.67
        ),
        CardTransaction(
            transaction_id = "txn001",
            date = "2024-07-01T10:15:30Z",
            merchant = "SuperMart",
            status = "success",
            type = "debit",
            amount = -45.67
        ),
    )
    private val cardTypeItem = listOf(
        PopupFilterModel(
            title = Constants.CardType.VISA.value,
            isSelect = false,
        ), PopupFilterModel(
            title = Constants.CardType.MASTERCARD.value,
            isSelect = false,
        )
    )

    private var cardDetailsCache: CardDetailResponse? = null
    private val viewModelState = MutableStateFlow(
        CardDetailState(
            isLoading = false,
            cardItem = cardItem,
            frequentlyItem = frequentlyItem,
            serviceItem = serviceItem,
            cardTransactionItem = cardTransactionItem.map { item->
                CardTransaction(
                    transaction_id = item.transaction_id,
                    date = item.date,
                    merchant = item.merchant,
                    status = item.status,
                    type = item.type,
                    amount = item.amount,
                    dateAndTime = getDateOnly(item.date),
                    year = getYearOnly(item.date),
                    month = getMonthAndYear(item.date),
                )
            },
            cardTypeItem = cardTypeItem,
        )
    )
    val uiState = viewModelState.asStateFlow()

    private fun getCardTransaction() {
        viewModelScope.launch {
            repository.getCardDetails().collect { response ->
                when (response) {
                    is Resource.Error -> {
                        viewModelState.update { it.copy(isLoading = false) }

                    }

                    is Resource.Loading -> {
                        viewModelState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        cardDetailsCache = response.data
                        viewModelState.update {
                            it.copy(
                                isLoading = false,
                                cardTransactionItem = response.data?.transactions?.map { item->
                                    CardTransaction(
                                        transaction_id = item.transaction_id,
                                        date = item.date,
                                        merchant = item.merchant,
                                        status = item.status,
                                        type = item.type,
                                        amount = item.amount,
                                        dateAndTime = getDateOnly(item.date),
                                        year = getYearOnly(item.date),
                                        month = getMonthAndYear(item.date),
                                    )
                                },
                                cardDetails = cardDetailsCache
                            )
                        }
                    }
                }
            }
        }
    }

    fun processIntent(intent: Intent?) {
        Log.e("processIntent","msg: ${intent.toJson()}")
        getCardTransaction()
        getCardDetail()
    }

    private fun getCardDetail() {
        val teamArray = mutableListOf<CardListItem>()
        viewModelState.value.cardItem?.let { teamArray.addAll(it) }
        viewModelScope.launch {
            repository.getCardItem.collect { item ->
                teamArray.addAll(item)
                viewModelState.update {
                    it.copy(
                        cardItem = teamArray.toSet().toList()
                    )
                }
            }
        }

    }

    fun onClickEvent(createCardEvents: CreateCardEvents) {
        when (createCardEvents) {
            is CreateCardEvents.OnCardCVV -> {
                viewModelState.update {
                    it.copy(
                        cardCVVNumber = createCardEvents.number,
                        lastUpdate = System.currentTimeMillis(),
                    )
                }
            }

            is CreateCardEvents.OnCardExpiry -> viewModelState.update {
                it.copy(
                    cardExpiry = createCardEvents.expiry,
                    lastUpdate = System.currentTimeMillis(),
                )
            }

            is CreateCardEvents.OnCardHolderName -> viewModelState.update {
                it.copy(
                    cardHolderName = createCardEvents.name,
                    lastUpdate = System.currentTimeMillis(),
                )
            }

            is CreateCardEvents.OnCardNumber -> viewModelState.update {
                it.copy(
                    cardNumber = createCardEvents.number,
                    lastUpdate = System.currentTimeMillis(),
                )
            }

            is CreateCardEvents.OnUpdateCardType -> viewModelState.update {
                it.copy(
                    cardType = createCardEvents.item.title,
                    lastUpdate = System.currentTimeMillis(),
                )
            }

            is CreateCardEvents.OnStatus -> viewModelState.update {
                it.copy(
                    status = false,
                    lastUpdate = System.currentTimeMillis(),
                )
            }
        }
    }

    fun onSaveCardDetail() {
        viewModelScope.launch {
            viewModelState.update {
                it.copy(
                    isLoading = true,
                    lastUpdate = System.currentTimeMillis(),
                )
            }
            repository.insertCardDetail(
                cardInfo = CardListItem(
                    cardName = "Dutch Bangla Bank",
                    cardType = "Platinum Plus",
                    holderName = viewModelState.value.cardHolderName.valueOrDefault(),
                    cardNumber = viewModelState.value.cardNumber.valueOrDefault(),
                    cardCategory = viewModelState.value.cardType.valueOrDefault(),
                    cardExpireDate = viewModelState.value.cardExpiry.valueOrDefault(),
                )
            )
            viewModelState.update {
                it.copy(
                    isLoading = false,
                    lastUpdate = System.currentTimeMillis(),
                    status = true,
                )
            }
        }
    }

}

data class CardDetailState(
    val isLoading: Boolean? = false,
    val status: Boolean? = false,
    val lastUpdate: Long = 0,
    val cardItem: List<CardListItem>? = arrayListOf(),
    val frequentlyItem: List<FrequentlyItem>? = listOf(),
    val serviceItem: List<FrequentlyItem>? = listOf(),
    val cardTransactionItem: List<CardTransaction>? = listOf(),
    val cardDetails: CardDetailResponse? = null,
    val cardTypeItem: List<PopupFilterModel>? = null,
    val cardHolderName: String? = "",
    val cardNumber: String? = "",
    val cardType: String? = "",
    val cardExpiry: String? = "",
    val cardCVVNumber: String? = "",
)

/*data class PopupFilterModel(
    val title: String? = null,
    val subTitle: String? = null,
    var isSelect: Boolean? = null,
    var status: Boolean? = null,
)*/
