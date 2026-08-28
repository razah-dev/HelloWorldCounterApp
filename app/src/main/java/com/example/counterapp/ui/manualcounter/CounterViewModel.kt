package com.example.counterapp.ui.manualcounter

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val LOG_TAG: String = "CounterViewModel"

class CounterViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<CounterUiState> =
        MutableStateFlow(CounterUiState())
    val uiState: StateFlow<CounterUiState> = _uiState.asStateFlow()

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
}
