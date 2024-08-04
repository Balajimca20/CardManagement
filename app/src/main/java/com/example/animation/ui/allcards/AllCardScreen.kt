package com.example.animation.ui.allcards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.animation.R
import com.example.animation.commonutils.valueOrDefault
import com.example.animation.data.model.CardListItem
import com.example.animation.ui.components.CardItem
import com.example.animation.ui.dashboard.CardDetailState


@Preview(showBackground = true)
@Composable
fun AllCardScreenPreview() {
    AllCardScreen(
        uiState = CardDetailState(
            cardItem = listOf(
                CardListItem(
                    cardName = "Dutch Bangla Bank",
                    cardNumber = "1234567890123456",
                    cardType = "Platinum Plus",
                    cardExpireDate = "Exp 01/22",
                    cardCategory = "VISA",
                    holderName = "Sunny Aveiro"
                ), CardListItem(
                    cardName = "Dutch Bangla Bank",
                    cardNumber = "1234567890123456",
                    cardType = "Platinum Plus",
                    cardExpireDate = "Exp 01/22",
                    cardCategory = "VISA",
                    holderName = "Sunny Aveiro"
                ), CardListItem(
                    cardName = "Dutch Bangla Bank",
                    cardNumber = "1234567890123456",
                    cardType = "Platinum Plus",
                    cardExpireDate = "Exp 01/22",
                    cardCategory = "VISA",
                    holderName = "Sunny Aveiro"
                ), CardListItem(
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
        )
    )
}


@Composable
fun AllCardScreen(uiState: CardDetailState) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.layout_bg)
    ) {
        Column {
            AllCardItem(cardListItems = uiState.cardItem ?: listOf())
        }
    }
}

@Composable
fun AllCardItem(cardListItems: List<CardListItem>) {
    LazyColumn {
        itemsIndexed(cardListItems) { index, item ->
            CardItem(
                index = index,
                cardName = item.cardName,
                cardNumber = item.cardNumber,
                cardType = item.cardType.valueOrDefault(),
                cardExpireDate = item.cardExpireDate,
                cardCategory = item.cardCategory.valueOrDefault(),
                holderName = item.holderName.valueOrDefault(),
                onClickCardItem = {},
            )

        }
    }
}
