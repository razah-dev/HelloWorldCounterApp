package com.example.counterapp.ui.cloudcontent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.counterapp.cloud.CloudApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val LOG_TAG: String = "CloudContentViewModel"

@HiltViewModel
class CloudContentViewModel @Inject constructor(
    private val cloudCounterApi: CloudApiService
) : ViewModel() {
    private val _uiState: MutableStateFlow<CloudContentUiState> =
        MutableStateFlow(CloudContentUiState())
    val uiState: StateFlow<CloudContentUiState> = _uiState.asStateFlow()

    fun contentFetch() {
        Log.i(LOG_TAG,  "ContentFetch called on ${_uiState.value}")

        viewModelScope.launch {
            val contentFetched: String = cloudCounterApi.getHello()

            _uiState.update { currentUiState ->
                currentUiState.copy(
                    contentFetched = contentFetched
                )
            }
        }
    }
}