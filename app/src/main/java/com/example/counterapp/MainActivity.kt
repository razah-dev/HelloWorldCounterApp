package com.example.counterapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.counterapp.ui.autocounter.AutoCounterScreenContent
import com.example.counterapp.ui.autocounter.AutoCounterUiState
import com.example.counterapp.ui.autocounter.AutoCounterViewModel
import com.example.counterapp.ui.manualcounter.ManualCounterUiState
import com.example.counterapp.ui.manualcounter.ManualCounterViewModel
import com.example.counterapp.ui.manualcounter.ManualCounterScreenContent
import com.example.counterapp.ui.theme.HelloWorldCounterAppTheme
import dagger.hilt.android.AndroidEntryPoint

private const val LOG_TAG: String = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelloWorldCounterAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MainCountersScreen(
                        userName = "Raza Hussain",
                        modifier =
                            Modifier
                                .padding(innerPadding)
                                .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun MainCountersScreen(
    userName: String,
    modifier: Modifier = Modifier,
    manualCounterViewModel: ManualCounterViewModel = hiltViewModel(),
    autoCounterViewModel: AutoCounterViewModel = hiltViewModel(),
) {
    Log.i(LOG_TAG,  "CounterScreen started")
    val manualCounterUiState: ManualCounterUiState by manualCounterViewModel.uiState.collectAsStateWithLifecycle()
    val autoCounterUiState: AutoCounterUiState by autoCounterViewModel.uiState.collectAsStateWithLifecycle()

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ManualCounterScreenContent(
            userName = userName,
            counterValue = manualCounterUiState.counter,
            onIncrement = { manualCounterViewModel.incrementCounter() },
            onReset = { manualCounterViewModel.resetCounter() },
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

@Preview(showBackground = true)
@Composable
fun ManualCounterScreenContentPreview() {
    HelloWorldCounterAppTheme {
        ManualCounterScreenContent(
            userName = "Raza",
            counterValue = 120,
            onIncrement = { },
            onReset = { },
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AutoCounterScreenContentPreview() {
    HelloWorldCounterAppTheme {
        AutoCounterScreenContent(
            counterValue = 512,
            isAutoIncrementStopped = true,
            onCounterStart = { },
            onCounterStop = { },
            onCounterReset = { },
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
    }
}
