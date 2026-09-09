package com.example.counterapp.ui.fetchcontent

import com.example.counterapp.ui.manualcounter.CounterDataUiEntry

data class FetchContentUiState(
    val isContentFetched: Boolean = false,
    val counterDataList: List<CounterDataUiEntry> = listOf()
)
