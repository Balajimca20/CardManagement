package com.example.animation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.animation.R
import com.example.animation.commonutils.Constants
import com.example.animation.commonutils.maskString
import com.example.animation.data.model.CardListItem
import com.example.animation.ui.navigation.BottomNavigationComponent
import com.example.animation.ui.navigation.FrequentlyItem
import com.example.animation.ui.utils.getColorItem

@Preview(showBackground = true)
@Composable
fun CardItemPreview() {
    DashboardCardItem(
        arrayListOf(
            CardListItem(
                cardName = "Dutch Bangla Bank",
                cardNumber = "1234567890123456",
                cardType = "Platinum Plus",
                cardExpireDate = "Exp 01/22",
                cardCategory = Constants.CardType.MASTERCARD.value,
                holderName = "Sunny Aveiro"
            ),
            CardListItem(
                cardName = "Dutch Bangla Bank",
                cardNumber = "1234567890123456",
                cardType = "Platinum Plus",
                cardExpireDate = "Exp 01/22",
                cardCategory = Constants.CardType.VISA.value,
                holderName = "Sunny Aveiro"
            ),
            CardListItem(
                cardName = "Dutch Bangla Bank",
                cardNumber = "1234567890123456",
                cardType = "Platinum Plus",
                cardExpireDate = "Exp 01/22",
                cardCategory = Constants.CardType.VISA.value,
                holderName = "Sunny Aveiro"
            )
        ),
        onClickCardItem = {}
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardCardItem(
    cardItem: List<CardListItem>?,
    onClickCardItem: (CardListItem?) -> Unit,
) {

    val pagerState = rememberPagerState(pageCount = {
        cardItem?.size ?: 0
    })
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(start = 32.dp, end = 32.dp),
    ) { page ->
        CardItem(
            index = page,
            cardName = cardItem?.get(page)?.cardName ?: "",
            cardNumber = cardItem?.get(page)?.cardNumber ?: "",
            cardType = cardItem?.get(page)?.cardType ?: "",
            cardExpireDate = cardItem?.get(page)?.cardExpireDate ?: "",
            cardCategory = cardItem?.get(page)?.cardCategory ?: "",
            holderName = cardItem?.get(page)?.holderName ?: "",
            onClickCardItem = { onClickCardItem(cardItem?.get(page)) },
        )
    }
}


@Composable
fun CardItem(
    index: Int = 0,
    cardName: String,
    cardNumber: String,
    cardType: String,
    cardExpireDate: String,
    cardCategory: String,
    holderName: String,
    onClickCardItem: () -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClickCardItem()
            },
        shape = RoundedCornerShape(8.dp),
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = getColorItem(index)
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    modifier = Modifier.alpha(0.2f),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_visa),
                    contentDescription = "visa",
                    tint = colorResource(id = R.color.visa_text_clg)
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_bank),
                        contentDescription = "icon",
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = cardName,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = maskString(cardNumber),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = cardType,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = cardExpireDate,
                        color = Color.White,
                        fontSize = 12.sp,
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = holderName,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )

                    Image(
                        modifier = Modifier
                            .size(width = 32.dp, height = 16.dp),
                        imageVector = if (cardCategory.equals(Constants.CardType.VISA.value, true))
                            ImageVector.vectorResource(id = R.drawable.ic_visa) else
                            ImageVector.vectorResource(id = R.drawable.ic_mastercard),
                        contentDescription = "visa",
                    )


                }


            }


        }
    }
}


@Preview(showBackground = true)
@Composable
fun FrequentlyUsedPreview() {
    DashboardFrequentItem(
        onClickItem = {},
        frequentlyItem = listOf(
            FrequentlyItem.MobileRecharge,
            FrequentlyItem.BillPayment,
            FrequentlyItem.BankTransfer,
            FrequentlyItem.RequestMoney,
            FrequentlyItem.TransferHistory,
        )
    )
}

@Composable
fun DashboardFrequentItem(
    onClickItem: (FrequentlyItem) -> Unit,
    frequentlyItem: List<FrequentlyItem>?
) {
    Column {
        SubTitleContent(
            stringResource(id = R.string.frequently_used)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            items(frequentlyItem ?: listOf()) { item ->
                FrequentItem(
                    item,
                    onClickItem = onClickItem
                )
            }
        }
    }

}

@Composable
fun FrequentItem(
    item: FrequentlyItem,
    onClickItem: (FrequentlyItem) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(modifier = Modifier
            .background(
                color = colorResource(id = item.bgColor),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
            onClick = {
                onClickItem(item)
            }
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = "icon",
                tint = colorResource(id = item.color)

            )
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = stringResource(id = item.title),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = colorResource(id = R.color.text_color)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardServiceItemPreview() {
    DashboardServiceItem(
        onClickItem = {},
        serviceItem = listOf(
            FrequentlyItem.OpenAccount,
            FrequentlyItem.ManageCards,
        )
    )
}

@Composable
fun DashboardServiceItem(
    onClickItem: (FrequentlyItem) -> Unit,
    serviceItem: List<FrequentlyItem>?
) {
    Column {
        SubTitleContent(
            stringResource(id = R.string.service)
        )
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth(),
            columns = GridCells.Fixed(2)
        ) {
            items(serviceItem ?: listOf()) { item ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    ) {
                        IconButton(modifier = Modifier
                            .background(
                                color = colorResource(id = item.bgColor),
                                shape = RoundedCornerShape(30)
                            ),
                            onClick = {
                                onClickItem(item)
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = item.icon,
                                contentDescription = "icon",
                                tint = colorResource(id = item.color)

                            )
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = stringResource(id = item.title),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(id = R.color.text_color)
                        )
                    }

                }
            }
        }
    }

}

@Composable
fun SubTitleContent(title: String) {
    Text(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        text = title,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = colorResource(id = R.color.credit_text_clg)
    )
}

@Composable
fun PlaceHolderContent(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = colorResource(id = R.color.credit_text_clg)
    )
}

@Preview
@Composable
fun CustomToolbarPreview() {
    CustomToolbar(
        onClickBack = {},
        onClickNotify = {},
        title = "Dashboard"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbar(
    onClickBack: () -> Unit,
    onClickNotify: () -> Unit,
    title: String,
    isBackVisible: Boolean = false
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.layout_bg)
        ),
        title = {
            Text(
                text = title,
                color = colorResource(id = R.color.text_color),
                fontWeight = FontWeight.Medium,
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                if (isBackVisible)
                    onClickBack()
            }) {
                Icon(
                    imageVector = if (isBackVisible) Icons.Default.ArrowBackIosNew
                    else Icons.Default.Dashboard,
                    contentDescription = "menu",
                    tint = colorResource(id = R.color.text_color)
                )
            }
        },
        actions = {
            IconButton(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    ),
                onClick = {
                    onClickNotify()
                }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "dashboard",
                    tint = colorResource(id = R.color.unselect)
                )
            }
        }
    )
}


@Composable
fun CustomBottomAppBar(
    navController: NavHostController,
    state: MutableState<Boolean>,
    modifier: Modifier,
    onClickAddNewCard: () -> Unit,
) {
    Box(contentAlignment = Alignment.Center) {
        BottomAppBar(
            containerColor = colorResource(id = R.color.layout_bg),
            content = {
                BottomNavigationComponent(
                    navController = navController,
                    state = state,
                    modifier = modifier
                )
            }
        )
        FloatingActionButton(
            modifier = Modifier.offset(y = ((-42).dp)),
            shape = RoundedCornerShape(50),
            onClick = onClickAddNewCard,
            contentColor = Color.White,
            containerColor = colorResource(id = R.color.primary)
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Add icon")
        }
    }

}





