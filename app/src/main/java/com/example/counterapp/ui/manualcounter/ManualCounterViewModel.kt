package com.example.counterapp.ui.manualcounter

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val LOG_TAG: String = "ManualCounterViewModel"

@HiltViewModel
class ManualCounterViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<ManualCounterUiState> =
        MutableStateFlow(ManualCounterUiState())
    val uiState: StateFlow<ManualCounterUiState> = _uiState.asStateFlow()

    fun incrementCounter() {
        Log.i(LOG_TAG,  "IncrementCounter called on ${_uiState.value}")
        _uiState.update { currentUiState ->
            currentUiState.copy(
                counter = currentUiState.counter + 1
            )
        }
    }

    fun resetCounter() {
        Log.i(LOG_TAG,  "ResetCounter called on ${_uiState.value}")
        _uiState.update { currentUiState ->
            currentUiState.copy(
                counter = 0
            )
        }
    }

    fun saveToCloudCounter() {
        Log.i(LOG_TAG,  "SaveToCloudCounter called on ${_uiState.value}")
    }
}
