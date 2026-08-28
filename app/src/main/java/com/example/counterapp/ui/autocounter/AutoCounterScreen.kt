package com.example.counterapp.ui.autocounter

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.counterapp.ui.utils.CounterButtonComposable
import com.example.counterapp.ui.utils.CounterTextComposable

private const val LOG_TAG: String = "AutoCounterScreen"

@Composable
fun AutoCounterScreenContent(
    counterValue: Int,
    isAutoIncrementStopped: Boolean,
    onCounterStart: () -> Unit,
    onCounterStop: () -> Unit,
    onCounterReset: () -> Unit,
    verticalArrangement: Arrangement.Vertical,
    horizontalAlignment: Alignment.Horizontal,
    modifier: Modifier = Modifier,
) {
    Log.i(LOG_TAG,  "AutoCounterScreenContent started")

    Column (
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        CounterTextComposable(
            text = "Auto Counter: $counterValue"
        )
        if (isAutoIncrementStopped) {
            CounterButtonComposable(
                buttonText = "Auto Counter Start",
                onButtonClick = onCounterStart
            )
        } else {
            CounterButtonComposable(
                buttonText = "Auto Counter Stop",
                onButtonClick = onCounterStop
            )
        }
        CounterButtonComposable(
            buttonText = "Auto Counter Reset",
            onButtonClick = onCounterReset
        )
    }
}
