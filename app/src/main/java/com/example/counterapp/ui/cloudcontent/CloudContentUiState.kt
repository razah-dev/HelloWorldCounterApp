package com.example.counterapp.ui.cloudcontent

import com.example.counterapp.ui.manualcounter.CounterDataUiEntry

data class CloudContentUiState(
    val isContentFetched: Boolean = false,
    val counterDataUiEntryList: List<CounterDataUiEntry> = listOf()
)
