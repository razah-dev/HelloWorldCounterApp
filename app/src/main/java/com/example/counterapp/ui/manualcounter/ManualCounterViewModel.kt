package com.example.counterapp.ui.manualcounter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.counterapp.cloud.CloudApiService
import com.example.counterapp.data.CounterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Clock

private const val LOG_TAG: String = "ManualCounterViewModel"

@HiltViewModel
class ManualCounterViewModel @Inject constructor(
    private val cloudCounterApi: CloudApiService
) : ViewModel() {
    private val _uiState: MutableStateFlow<ManualCounterUiState> =
        MutableStateFlow(ManualCounterUiState())
    val uiState: StateFlow<ManualCounterUiState> = _uiState.asStateFlow()

    fun setUsername(username: String) {
        Log.i(LOG_TAG,  "SetUsername called on ${_uiState.value}")
        _uiState.update { currentUiState ->
            currentUiState.copy(
                username = username
            )
        }
    }

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
        val counterData = CounterData(
            username = _uiState.value.username,
            timestamp8601ISOFormat = Clock.System.now().toString(),
            counterValue = _uiState.value.counter
        )
        viewModelScope.launch {
            val result: CounterData = cloudCounterApi.saveManualCounterData(counterData = counterData)
            Log.i(LOG_TAG,  "SaveToCloudCounter RESULT: $result")
        }
    }
}
