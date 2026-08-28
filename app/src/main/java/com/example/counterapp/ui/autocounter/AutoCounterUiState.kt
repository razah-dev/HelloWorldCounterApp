package com.example.counterapp.ui.autocounter

data class AutoCounterUiState(
    val isAutoIncrementStopped: Boolean = true,
    val counter: Int = 0
)