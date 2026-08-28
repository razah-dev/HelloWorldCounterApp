package com.example.counterapp.ui.autocounter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val LOG_TAG: String = "AutoIncrementCounterViewModel"

class AutoIncrementCounterViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<AutoCounterUiState> =
        MutableStateFlow(AutoCounterUiState())
    val uiState: StateFlow<AutoCounterUiState> = _uiState.asStateFlow()

    var autoCounterJob: Job? = null

    fun autoIncrementStart() {
        Log.i(LOG_TAG,  "AutoIncrementStart called on ${_uiState.value}")
        _uiState.update { currentUiState ->
            currentUiState.copy(
                isAutoIncrementStopped = false
            )
        }
        cancelJob()
        autoCounterJob = viewModelScope.launch {
            autoIncrementJob()
        }
    }

    private suspend fun autoIncrementJob() {
        while (true) {
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    counter = currentUiState.counter + 1
                )
            }
            delay(timeMillis = 1000)
        }
    }

    fun autoIncrementStop() {
        Log.i(LOG_TAG,  "AutoIncrementStop called on ${_uiState.value}")
        cancelJob()
        _uiState.update { currentUiState ->
            currentUiState.copy(
                isAutoIncrementStopped = true
            )
        }
    }

    fun autoIncrementReset() {
        Log.i(LOG_TAG,  "AutoIncrementReset called on ${_uiState.value}")
        _uiState.update { currentUiState ->
            currentUiState.copy(
                isAutoIncrementStopped = true,
                counter = 0
            )
        }
        cancelJob()
    }

    private fun cancelJob() {
        autoCounterJob?.cancel()
        autoCounterJob = null
    }
}
