package com.example.counterapp.ui.fetchfiles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.counterapp.data.CounterData
import com.example.counterapp.data.CounterDataRepository
import com.example.counterapp.data.cloud.CloudFileData
import com.example.counterapp.modules.CloudDataRepository
import com.example.counterapp.ui.manualcounter.CounterDataUiEntry
import com.example.counterapp.ui.utils.shortHumanReadableTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val LOG_TAG: String = "FetchFilesViewModel"

@HiltViewModel
class FetchFilesViewModel @Inject constructor(
    @CloudDataRepository private val cloudDataRepository: CounterDataRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<FetchFilesUiState> =
        MutableStateFlow(FetchFilesUiState())
    val uiState: StateFlow<FetchFilesUiState> = _uiState.asStateFlow()

    fun fetchAllFileIdsDataList() {
        Log.i(LOG_TAG,  "FetchAllFileIdsDataList called on ${_uiState.value}")

        viewModelScope.launch {
            val allFileIdsList: List<CloudFileData> = cloudDataRepository.fetchAllFileIdsDataList()

            _uiState.update { currentUiState ->
                currentUiState.copy(
                    isFetchedFileIdsForDevice = true,
                    allFileIdsForDevice = allFileIdsList.map { it.fileId }
                )
            }
        }
    }
}
