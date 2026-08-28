package com.example.counterapp.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.counterapp.ui.autocounter.AutoCounterScreenContent
import com.example.counterapp.ui.autocounter.AutoCounterUiState
import com.example.counterapp.ui.autocounter.AutoIncrementCounterViewModel
import com.example.counterapp.ui.manualcounter.CounterUiState
import com.example.counterapp.ui.manualcounter.CounterViewModel
import com.example.counterapp.ui.manualcounter.ManualCounterScreenContent

private const val LOG_TAG: String = "CounterScreen"

@Composable
fun MainCountersScreen(
    userName: String,
    modifier: Modifier = Modifier,
    counterViewModel: CounterViewModel = viewModel(),
    autoCounterViewModel: AutoIncrementCounterViewModel = viewModel(),
) {
    Log.i(LOG_TAG,  "CounterScreen started")
    val counterUiState: CounterUiState by counterViewModel.uiState.collectAsStateWithLifecycle()
    val autoCounterUiState: AutoCounterUiState by autoCounterViewModel.uiState.collectAsStateWithLifecycle()

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ManualCounterScreenContent(
            userName = userName,
            counterValue = counterUiState.counter,
            onIncrement = { counterViewModel.incrementCounter() },
            onReset = { counterViewModel.resetCounter() },
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        )
        AutoCounterScreenContent(
            counterValue = autoCounterUiState.counter,
            isAutoIncrementStopped = autoCounterUiState.isAutoIncrementStopped,
            onCounterStart = { autoCounterViewModel.autoIncrementStart() },
            onCounterStop = { autoCounterViewModel.autoIncrementStop() },
            onCounterReset = { autoCounterViewModel.autoIncrementReset() },
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        )
    }
}
