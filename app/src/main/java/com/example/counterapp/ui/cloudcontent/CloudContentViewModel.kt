package com.example.counterapp.ui.cloudcontent

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
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import javax.inject.Inject

private const val LOG_TAG: String = "CloudContentViewModel"
private val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")

@HiltViewModel
class CloudContentViewModel @Inject constructor(
    private val cloudCounterApi: CloudApiService
) : ViewModel() {
    private val _uiState: MutableStateFlow<CloudContentUiState> =
        MutableStateFlow(CloudContentUiState())
    val uiState: StateFlow<CloudContentUiState> = _uiState.asStateFlow()

    fun fetchCounterDataList() {
        Log.i(LOG_TAG,  "FetchCounterDataList called on ${_uiState.value}")

        viewModelScope.launch {
            val counterDataList: List<CounterData> = cloudCounterApi.fetchCounterDataList()

            _uiState.update { currentUiState ->
                currentUiState.copy(
                    isContentFetched = true,
                    counterDataUiEntryList = counterDataList.map { counterData ->
                        val zonedDateTime: ZonedDateTime = Instant
                            .parse(counterData.timestamp8601ISOFormat)
                            .atZone(ZoneId.of("Z"))  // UTC Time Zone
                        CounterDataUiEntry(
                            username = counterData.username,
                            timestampHumanReadable =
                                "${zonedDateTime.format(DATE_TIME_FORMATTER)} UTC",
                            counterValue = counterData.counterValue
                        )
                    }
                )
            }
        }
    }
}
