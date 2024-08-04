package com.example.animation.ui.cardsdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animation.R
import com.example.animation.commonutils.Constants
import com.example.animation.commonutils.getDateAndTime
import com.example.animation.data.model.CardTransaction
import com.example.animation.ui.components.SubTitleContent
import com.example.animation.ui.dashboard.CardDetailState
import com.example.animation.ui.utils.isNegative
import com.example.animation.ui.utils.removeNegativeSign


@Preview(showBackground = true)
@Composable
fun CardDetailScreenPreview() {
    CardDetailScreen(
        uiState = CardDetailState(
            cardTransactionItem = listOf(
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
        )
    )
}

@Composable
fun CardDetailScreen(
    uiState: CardDetailState = CardDetailState(),
) {
    var selectChartType by rememberSaveable { mutableStateOf(Constants.ChartType.DAY.value) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.layout_bg)
    ) {
        if (uiState.isLoading == true) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colorResource(id = R.color.primary))
            }
        } else {
            AnimatedVisibility(visible = true) {
                Column(Modifier.fillMaxWidth()) {

                    CardSummaryItem(currentBalance = uiState.cardDetails?.cardSummary?.currentBalance.toString())

                    ChartScheduleType(
                        onClickChartType = {
                            selectChartType = it
                        },
                        selectChartType = selectChartType
                    )

                    Spacer(modifier = Modifier.padding(4.dp))

                    LineChartScreen(selectChartType, uiState.cardTransactionItem)

                    Spacer(modifier = Modifier.padding(4.dp))

                    SubTitleContent(title = stringResource(id = R.string.transfer_history_str))

                    AllCardItem(transactionList = uiState.cardTransactionItem ?: listOf())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardSummaryItemPreview() {
    CardSummaryItem(
        currentBalance = "3567.12"
    )
}

@Composable
fun CardSummaryItem(
    currentBalance: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.card_summary)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.current_balance),
                color = colorResource(id = R.color.current_balance),
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = stringResource(id = R.string.current_balance_dollar, currentBalance),
                color = colorResource(id = R.color.primary),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun AllCardItemPreview() {
    val tempITem = listOf(
        CardTransaction(
            transaction_id = "txn001",
            date = "2024-07-01T10:15:30Z",
            merchant = "SuperMart",
            status = "success",
            type = "debit",
            amount = -45.67
        )
    )
    AllCardItem(tempITem)
}

@Composable
fun AllCardItem(transactionList: List<CardTransaction>) {
    LazyColumn {
        items(transactionList) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 1.dp)
                    .background(color = colorResource(id = R.color.white))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .background(
                            color = colorResource(id = R.color.credit_bg),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .size(48.dp)
                        .padding(12.dp),
                    imageVector = if (item.type == Constants.TransactionType.DEBIT.value)
                        Icons.AutoMirrored.Filled.ArrowForward else
                        Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "debit",
                    tint = if (item.type == Constants.TransactionType.DEBIT.value)
                        colorResource(id = R.color.debit) else
                        colorResource(id = R.color.credit),
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = item.merchant ?: "",
                        color = colorResource(id = R.color.credit_text_clg),
                    )
                    Text(
                        text = getDateAndTime(item.date),
                        color = colorResource(id = R.color.unselect),
                    )
                }

                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = if (isNegative(item.amount ?: 0.00)) stringResource(
                        id = R.string.current_balance_debit,
                        removeNegativeSign(item.amount ?: 0.00)
                    )
                    else stringResource(
                        id = R.string.current_balance_credit,
                        removeNegativeSign(item.amount ?: 0.00)
                    ),
                    color = if (item.type == Constants.TransactionType.DEBIT.value)
                        colorResource(id = R.color.credit_text_clg) else
                        colorResource(id = R.color.primary),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

            }
        }
    }
}

@Composable
fun ChartScheduleType(
    onClickChartType: (String) -> Unit,
    selectChartType: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SingleChoiceButton(
                onClickChartType = onClickChartType,
                selectChartType = selectChartType,
                title = Constants.ChartType.DAY.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            SingleChoiceButton(
                onClickChartType = onClickChartType,
                selectChartType = selectChartType,
                title = Constants.ChartType.MONTH.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            SingleChoiceButton(
                onClickChartType = onClickChartType,
                selectChartType = selectChartType,
                title = Constants.ChartType.YEARLY.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}

@Composable
fun SingleChoiceButton(
    onClickChartType: (String) -> Unit,
    selectChartType: String,
    modifier: Modifier = Modifier,
    title: String,
) {
    Button(
        modifier = modifier,
        onClick = {
            onClickChartType(title)
        },
        colors = if (title == selectChartType) {
            ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primary),
                contentColor = colorResource(id = R.color.white)
            )
        } else {
            ButtonDefaults.outlinedButtonColors(
                contentColor = Color.LightGray
            )
        }
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
        )
    }
}


