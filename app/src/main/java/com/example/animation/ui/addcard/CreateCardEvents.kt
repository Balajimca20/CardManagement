package com.example.animation.ui.addcard

sealed class CreateCardEvents {
    data class OnCardHolderName(val name: String) : CreateCardEvents()
    data class OnCardNumber(val number: String) : CreateCardEvents()
    data class OnCardExpiry(val expiry: String) : CreateCardEvents()
    data class OnCardCVV(val number: String) : CreateCardEvents()
    data class OnStatus(val status: Boolean) : CreateCardEvents()
    data class OnUpdateCardType(val item: PopupFilterModel) : CreateCardEvents()
}