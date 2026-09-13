package com.example.counterapp.ui.fetchfiles

data class FetchFilesUiState(
    val isFetchedFileIdsForDevice: Boolean = false,
    val allFileIdsForDevice: List<Int> = emptyList()
)
