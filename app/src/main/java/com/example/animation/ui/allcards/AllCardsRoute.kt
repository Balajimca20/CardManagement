package com.example.animation.ui.allcards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.animation.ui.dashboard.MainViewModel


@Composable
fun ALLCardRoute(viewModel: MainViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    AllCardScreen(uiState)
}