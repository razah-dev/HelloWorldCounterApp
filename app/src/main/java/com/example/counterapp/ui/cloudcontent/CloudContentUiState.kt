package com.example.counterapp.ui.cloudcontent

data class CounterDataUiEntry(
    val username: String,
    val timestampHumanReadable: String,
    val counterValue: Int
)

data class CloudContentUiState(
    val isContentFetched: Boolean = false,
    val counterDataUiEntryList: List<CounterDataUiEntry> = listOf()
)
