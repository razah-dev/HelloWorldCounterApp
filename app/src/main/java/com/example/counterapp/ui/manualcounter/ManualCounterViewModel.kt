package com.example.counterapp.ui.manualcounter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.counterapp.data.CounterData
import com.example.counterapp.data.CounterDataRepository
import com.example.counterapp.modules.CloudDataRepository
import com.example.counterapp.modules.LocalDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.time.Clock

private const val LOG_TAG: String = "ManualCounterViewModel"
val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")

@HiltViewModel
class ManualCounterViewModel @Inject constructor(
    @CloudDataRepository private val cloudDataRepository: CounterDataRepository,
    @LocalDataRepository private val localDataRepository: CounterDataRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<CounterDataUiEntry> =
        MutableStateFlow(CounterDataUiEntry())
    val uiState: StateFlow<CounterDataUiEntry> = _uiState.asStateFlow()

    fun setUsername(username: String) {
        Log.i(LOG_TAG,  "SetUsername called on ${_uiState.value}")
        _uiState.update { currentUiState ->
            currentUiState.copy(
                username = username
            )
        }
    }

    fun timestampProcess() {
        Log.i(LOG_TAG,  "TimestampProcess called on ${_uiState.value}")
        // TODO(raza): Replace with Kotlin flows so that we do not have a
        //  while(true) loop which would consume unnecessary cpu cycles
        //  and drain device battery.
        viewModelScope.launch {
            while (true) {
                _uiState.update { currentUiState ->
                    val zonedDateTime: ZonedDateTime = Instant
                        .parse(Clock.System.now().toString())
                        .atZone(ZoneId.of("Z"))  // UTC Time Zone
                    currentUiState.copy(
                        timestampHumanReadable = "${zonedDateTime.format(DATE_TIME_FORMATTER)} UTC"
                    )
                }
                delay(timeMillis = 1000)
            }
        }
    }

    fun incrementCounter() {
        Log.i(LOG_TAG,  "IncrementCounter called on ${_uiState.value}")
        _uiState.update { currentUiState ->
            currentUiState.copy(
                counterValue = currentUiState.counterValue + 1
            )
        }
    }

    fun resetCounter() {
        Log.i(LOG_TAG,  "ResetCounter called on ${_uiState.value}")
        _uiState.update { currentUiState ->
            currentUiState.copy(
                counterValue = 0
            )
        }
    }

    fun saveToCloudCounter() {
        Log.i(LOG_TAG,  "SaveToCloudCounter called on ${_uiState.value}")
        val counterData = CounterData(
            username = _uiState.value.username,
            timestamp8601ISOFormat = Clock.System.now().toString(),
            counterValue = _uiState.value.counterValue
        )
        viewModelScope.launch {
            val result: CounterData =
                cloudDataRepository.saveCounterData(counterData = counterData)
            Log.i(LOG_TAG,  "SaveToCloudCounter RESULT: $result")
        }
    }

    fun saveToLocalCounter() {
        Log.i(LOG_TAG,  "SaveToLocalCounter called on ${_uiState.value}")
        val counterData = CounterData(
            username = _uiState.value.username,
            timestamp8601ISOFormat = Clock.System.now().toString(),
            counterValue = _uiState.value.counterValue
        )
        viewModelScope.launch {
            val result: CounterData =
                localDataRepository.saveCounterData(counterData = counterData)
            Log.i(LOG_TAG,  "SaveToCloudCounter RESULT: $result")
        }
    }
}
