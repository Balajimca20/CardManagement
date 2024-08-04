package com.example.animation.ui.addcard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.animation.ui.dashboard.MainViewModel

@Composable
fun AddCardRoute(
    viewModel: MainViewModel,
    onBack :()->Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    AddCardScreen(
        uiState = uiState,
        onEventClicked =viewModel::onClickEvent,
        onSave=viewModel::onSaveCardDetail,
        onClose = {
            onBack()
        }
    )
}