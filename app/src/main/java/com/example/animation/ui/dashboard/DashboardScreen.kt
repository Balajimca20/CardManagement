package com.example.animation.ui.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.animation.R
import com.example.animation.data.model.CardListItem
import com.example.animation.ui.components.CustomBottomAppBar
import com.example.animation.ui.components.CustomToolbar
import com.example.animation.ui.components.DashboardCardItem
import com.example.animation.ui.components.DashboardFrequentItem
import com.example.animation.ui.components.DashboardServiceItem
import com.example.animation.ui.navigation.BottomBarNavItem
import com.example.animation.ui.navigation.FrequentlyItem
import com.example.animation.ui.navigation.NavigationGraph


@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(
        uiState = CardDetailState(
            cardItem = arrayListOf(
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
            ),
            frequentlyItem = listOf(
                FrequentlyItem.MobileRecharge,
                FrequentlyItem.BillPayment,
                FrequentlyItem.BankTransfer,
                FrequentlyItem.RequestMoney,
                FrequentlyItem.TransferHistory,
            ),
            serviceItem = listOf(
                FrequentlyItem.OpenAccount,
                FrequentlyItem.ManageCards,
            )
        ),
        onClickCardItem = {}
    )
}


@Composable
fun DashboardScreen(
    uiState: CardDetailState,
    onClickCardItem: (CardListItem?) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.layout_bg)
    ) {
        Column {
            DashboardCardItem(
                cardItem = uiState.cardItem?: listOf(),
                onClickCardItem = onClickCardItem
            )
            DashboardFrequentItem(
                onClickItem = {},
                frequentlyItem = uiState.frequentlyItem
            )
            Spacer(modifier = Modifier.padding(8.dp))
            DashboardServiceItem(
                onClickItem = {},
                serviceItem = uiState.serviceItem
            )

        }
    }
}


@Composable
fun MainScreenContent(viewModel: MainViewModel) {
    val navController: NavHostController = rememberNavController()
    val buttonsVisible = rememberSaveable { mutableStateOf(false) }
    val title = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            title.value = destination.route ?: ""
            buttonsVisible.value = navController.previousBackStackEntry != null
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            CustomToolbar(
                onClickBack = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                        buttonsVisible.value = navController.previousBackStackEntry != null
                    }
                },
                onClickNotify = {},
                title = title.value,
                isBackVisible = buttonsVisible.value
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = colorResource(id = R.color.layout_bg))
            ) {
                NavigationGraph(navController = navController, viewModel = viewModel)
            }
        },
        bottomBar = {
            AnimatedVisibility(visible = navController.currentBackStackEntryAsState().value?.destination?.route != BottomBarNavItem.AddCard.route) {
                CustomBottomAppBar(
                    navController = navController,
                    state = buttonsVisible,
                    modifier = Modifier,
                    onClickAddNewCard={
                        navController.navigate(BottomBarNavItem.AddCard.route) {
                            navController.graph.startDestinationRoute?.let { screenRoute ->
                                popUpTo(screenRoute) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }


        },
    )
}