package com.example.animation.ui.addcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animation.R
import com.example.animation.commonutils.Constants
import com.example.animation.commonutils.valueOrDefault
import com.example.animation.data.model.CardListItem
import com.example.animation.ui.components.PlaceHolderContent
import com.example.animation.ui.dashboard.CardDetailState
import com.example.animation.ui.navigation.FrequentlyItem
import kotlinx.coroutines.launch


@Preview(showBackground = true)
@Composable
fun AddCardScreenPreview() {
    AddCardScreen(
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
        onEventClicked = {},
        onSave = {},
        onClose = {},
    )
}


@Composable
fun AddCardScreen(
    uiState: CardDetailState,
    onEventClicked: (CreateCardEvents) -> Unit,
    onSave :()->Unit,
    onClose :()->Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val status= stringResource(id = R.string.your_card_details_successfully_added)
    LaunchedEffect(key1 = uiState.status) {
        if (uiState.status==true){
            snackBarHostState.showSnackbar(status)
            onEventClicked(CreateCardEvents.OnStatus(status = false))
            onClose()
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(id = R.color.layout_bg),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
            ) {
                Image(
                    modifier = Modifier
                        .align(
                            alignment = Alignment.CenterHorizontally
                        )
                        .size(128.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_card_info),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(8.dp))
                CommonText(
                    labelName = stringResource(id = R.string.cardholder_name),
                    placeHolder = Constants.PlaceholderType.NAME.value,
                    name = uiState.cardHolderName.valueOrDefault(),
                    onValueChange = { item ->
                        onEventClicked(CreateCardEvents.OnCardHolderName(name = item))

                    },
                    modifier = Modifier.fillMaxWidth()
                )
                CommonText(
                    labelName = stringResource(id = R.string.card_number),
                    placeHolder = Constants.PlaceholderType.NUMBER.value,
                    name = uiState.cardNumber.valueOrDefault(),
                    onValueChange = { item ->
                        onEventClicked(CreateCardEvents.OnCardNumber(number = item))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(4.dp))
                SingleChoiceDropDown(
                    placeholder = stringResource(id = R.string.card_type),
                    name = uiState.cardType,
                    dropDownItem = uiState.cardTypeItem,
                    onSingleSelection = { selectItem ->
                        onEventClicked(CreateCardEvents.OnUpdateCardType(item = selectItem))
                    },
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CommonText(
                        labelName = stringResource(id = R.string.expiry),
                        placeHolder = Constants.PlaceholderType.EXPIRY.value,
                        name = uiState.cardExpiry.valueOrDefault(),
                        onValueChange = { item ->
                            onEventClicked(CreateCardEvents.OnCardExpiry(expiry = item))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)

                    )
                    CommonText(
                        labelName = stringResource(id = R.string.cvv),
                        placeHolder = Constants.PlaceholderType.CVV.value,
                        name = uiState.cardCVVNumber.valueOrDefault(),
                        onValueChange = { item ->
                            onEventClicked(CreateCardEvents.OnCardCVV(number = item))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)

                    )
                }

            }
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = {
                    val validationContent = checkValidation(uiState)
                    if (validationContent != null) {
                        scope.launch {
                            snackBarHostState.showSnackbar(context.getString(validationContent))
                        }
                    }else {
                        onSave()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary),
                    contentColor = colorResource(id = R.color.white)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.add_card),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

    )


}

fun checkValidation(
    uiState: CardDetailState,
): Int? {
    val item = when {
        uiState.cardHolderName.isNullOrEmpty() -> R.string.enter_holder_Name
        uiState.cardNumber.isNullOrEmpty() || uiState.cardNumber.length != 16 -> R.string.enter_card_number
        uiState.cardType.isNullOrEmpty() -> R.string.choose_one_card_type
        uiState.cardExpiry.isNullOrEmpty() || uiState.cardExpiry.length != 5 -> R.string.enter_expiry
        uiState.cardCVVNumber.isNullOrEmpty() || uiState.cardCVVNumber.length != 3 -> R.string.enter_cvv_number
        else -> null
    }

    return item

}

@Composable
fun CommonText(
    labelName: String,
    placeHolder: String,
    name: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        PlaceHolderContent(title = labelName)
        Spacer(modifier = Modifier.padding(2.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = {
                if (it.length <= getMaxChar(placeHolder)) {
                    onValueChange(it)
                }
            },
            label = {
                Text(
                    text = placeHolder,
                    color = colorResource(id = R.color.unselect)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.primary),
                unfocusedBorderColor = Color.LightGray,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = getKeyboardType(placeHolder),
                imeAction = ImeAction.Next
            ),
            singleLine = true,
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleChoiceDropDown(
    placeholder: String?,
    name: String?,
    dropDownItem: List<PopupFilterModel>?,
    onSingleSelection: (PopupFilterModel) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf(name) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(placeholder ?: "")
        Spacer(modifier = Modifier.padding(4.dp))
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = title ?: "",
                onValueChange = {},
                singleLine = true,
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.primary),
                    unfocusedBorderColor = Color.LightGray,
                ),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
                dropDownItem?.forEach { item ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (title.equals(item.title, true) || item.isSelect == true)
                                    colorResource(id = R.color.current_balance)
                                else Color.White
                            ),
                        onClick = {
                            title = item.title.valueOrDefault()
                            onSingleSelection(item)
                            expanded = false
                        },
                        text = {
                            Text(
                                text = item.title.valueOrDefault(),
                                fontSize = 12.sp,
                                color = colorResource(id = R.color.text_color),
                                fontWeight = if (title.equals(item.title, true) ||
                                    item.isSelect == true
                                )
                                    FontWeight.SemiBold
                                else FontWeight.Normal
                            )
                        }
                    )
                }
            }
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

data class PopupFilterModel(
    val title: String? = null,
    val subTitle: String? = null,
    var isSelect: Boolean? = null,
    var status: Boolean? = null,
)
