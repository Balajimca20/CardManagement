package com.example.animation.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.animation.R


sealed class BottomBarNavItem(
    val title: Int,
    val icon : Int,
    val route: String,
) {
    data object Dashboard : BottomBarNavItem(
        title = R.string.dashboard,
        icon  =  R.drawable.ic_credit_card,
        route = "Dashboard",
    )
    data object CardDetail : BottomBarNavItem(
        title = R.string.card_detail,
        icon  =  R.drawable.ic_pie,
        route = "Card Details",
    )
    data object Empty : BottomBarNavItem(
        title = R.string.add_card,
        icon  =  R.drawable.ic_credit_card,
        route = "AddCard",
    )
    data object AllCards : BottomBarNavItem(
        title = R.string.all_card,
        icon  =  R.drawable.ic_wallet,
        route = "AllCards",
    )
    data object Profile : BottomBarNavItem(
        title = R.string.profile,
        icon  =  R.drawable.ic_person,
        route = "Profile",
    )
    data object AddCard : BottomBarNavItem(
        title = R.string.add_card,
        icon  =  R.drawable.ic_person,
        route = "AddCard",
    )
}

sealed class FrequentlyItem(
    val icon: ImageVector = Icons.Filled.Home,
    val title: Int,
    val bgColor: Int,
    val color: Int,
) {
    data object MobileRecharge : FrequentlyItem(
        title = R.string.mobile_recharge,
        icon = Icons.Default.Call,
        bgColor = R.color.recharge_light,
        color = R.color.recharge_dark,
    )

    data object BillPayment : FrequentlyItem(
        title = R.string.bill_payment,
        icon = Icons.Default.Payments,
        bgColor = R.color.bill_light,
        color = R.color.bill_dark,
    )

    data object BankTransfer : FrequentlyItem(
        title = R.string.bank_transfer,
        icon = Icons.AutoMirrored.Filled.Send,
        bgColor = R.color.transfer_light,
        color = R.color.transfer_dark,
    )

    data object RequestMoney : FrequentlyItem(
        title = R.string.request_money,
        icon = Icons.Default.RequestQuote,
        bgColor = R.color.request_light,
        color = R.color.request_dark,
    )

    data object TransferHistory : FrequentlyItem(
        title = R.string.transfer_history,
        icon = Icons.Default.History,
        bgColor = R.color.history_light,
        color = R.color.history_dark,
    )

    data object OpenAccount : FrequentlyItem(
        title = R.string.open_account,
        icon = Icons.Default.AccountTree,
        bgColor = R.color.recharge_light,
        color = R.color.recharge_dark,
    )

    data object ManageCards : FrequentlyItem(
        title = R.string.manage_cards,
        icon = Icons.Default.AddCard,
        bgColor = R.color.bill_light,
        color = R.color.bill_dark,
    )

}
