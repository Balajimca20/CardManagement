package com.example.animation.ui.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.animation.data.model.CardListItem

@Composable
fun DashboardRoute(
    viewModel: MainViewModel,
    onClickCardItem: (CardListItem?) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    DashboardScreen(
        uiState = uiState,
        onClickCardItem = onClickCardItem
    )
}


@Composable
fun ProfileRoute(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()

}