package com.example.counterapp.ui.fetchcontent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.counterapp.data.CounterData
import com.example.counterapp.data.CounterDataRepository
import com.example.counterapp.modules.LocalDataRepository
import com.example.counterapp.ui.manualcounter.CounterDataUiEntry
import com.example.counterapp.ui.utils.shortHumanReadableTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val LOG_TAG: String = "LocalContentViewModel"

@HiltViewModel
class LocalContentViewModel @Inject constructor(
    @LocalDataRepository private val localDataRepository: CounterDataRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<FetchContentUiState> =
        MutableStateFlow(FetchContentUiState())
    val uiState: StateFlow<FetchContentUiState> = _uiState.asStateFlow()

    fun fetchCounterData() {
        Log.i(LOG_TAG,  "FetchCounterData called on ${_uiState.value}")

        viewModelScope.launch {
            val counterDataList: List<CounterData> = localDataRepository.fetchCounterData()

            _uiState.update { currentUiState ->
                currentUiState.copy(
                    isContentFetched = true,
                    counterDataList = counterDataList.map { counterData ->
                        CounterDataUiEntry(
                            username = counterData.username,
                            timestampHumanReadable =
                                shortHumanReadableTimestamp(
                                    iso8601Timestamp =
                                        counterData.timestamp8601ISOFormat),
                            counterValue = counterData.counterValue
                        )
                    }
                )
            }
        }
    }
}
