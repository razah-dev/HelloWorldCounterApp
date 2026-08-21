package com.example.helloworldcounterapp.ui.screens

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

private const val LOG_TAG: String = "CounterScreen"

@Composable
fun CounterScreen(
    userName: String,
    modifier: Modifier = Modifier,
    viewModel: CounterViewModel = viewModel()
) {
    Log.i(LOG_TAG,  "Composable CounterScreen() called")
    val uiState: CounterUiState by viewModel.uiState.collectAsStateWithLifecycle()

    CounterScreenContent(
        userName = userName,
        counterValue = uiState.counter,
        onIncrement = { viewModel.incrementCounter() },
        onReset = { viewModel.resetCounter() },
        modifier = modifier
    )
}

@Composable
fun CounterScreenContent(
    userName: String,
    counterValue: Int,
    onIncrement: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Log.i(LOG_TAG,  "Composable CounterScreenContent() called")
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CounterTextComposable(
                text = "Greetings",
                textStyle = MaterialTheme.typography.displayMedium
            )
            CounterTextComposable(
                text = "$userName!",
                textStyle = MaterialTheme.typography.displayMedium
            )
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CounterTextComposable(
                text = "Counter Value:",
                textStyle = MaterialTheme.typography.displayMedium
            )
            CounterTextComposable(
                text = "$counterValue",
                textStyle = MaterialTheme.typography.displayMedium
            )
        }
        Column (
            verticalArrangement = Arrangement.spacedBy(60.dp)
        ) {
            CounterButtonComposable(
                buttonText = "Increment\nCounter",
                buttonTextStyle = MaterialTheme.typography.displayMedium,
                onButtonClick = onIncrement
            )
            CounterButtonComposable(
                buttonText = "Reset\nCounter",
                buttonTextStyle = MaterialTheme.typography.displayMedium,
                onButtonClick = onReset
            )
        }
    }
}

@Composable
fun CounterTextComposable(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Log.i(LOG_TAG,  "Composable CounterTextComposable() called with text = $text")

    Text(
        text = text,
        modifier = modifier,
        style = textStyle
    )
}

@Composable
fun CounterButtonComposable(
    buttonText: String,
    buttonTextStyle: TextStyle,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.i(LOG_TAG,  "Composable CounterButtonComposable() called with buttonText = $buttonText")

    FilledTonalButton(
        onClick = onButtonClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = buttonText,
            textAlign = TextAlign.Center,
            style = buttonTextStyle
        )
    }
}
