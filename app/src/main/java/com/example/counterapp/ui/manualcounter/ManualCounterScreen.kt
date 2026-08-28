package com.example.counterapp.ui.manualcounter

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.counterapp.ui.utils.CounterButtonComposable
import com.example.counterapp.ui.utils.CounterTextComposable

private const val LOG_TAG: String = "ManualCounterScreen"

@Composable
fun ManualCounterScreenContent(
    userName: String,
    counterValue: Int,
    onIncrement: () -> Unit,
    onReset: () -> Unit,
    verticalArrangement: Arrangement.Vertical,
    horizontalAlignment: Alignment.Horizontal,
    modifier: Modifier = Modifier,
) {
    Log.i(LOG_TAG,  "CounterScreenContent started")

    Column (
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        CounterTextComposable(
            text = "Greetings $userName!"
        )
        CounterTextComposable(
            text = "Counter: $counterValue"
        )
        CounterButtonComposable(
            buttonText = "Increment",
            onButtonClick = onIncrement
        )
        CounterButtonComposable(
            buttonText = "Reset",
            onButtonClick = onReset
        )
    }
}
