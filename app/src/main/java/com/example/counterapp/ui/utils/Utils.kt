package com.example.counterapp.ui.utils

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

private const val LOG_TAG: String = "Utils"

@Composable
fun CounterTextComposable(
    text: String,
    modifier: Modifier = Modifier
) {
    Log.i(LOG_TAG,  "CounterTextComposable started with text = $text")

    Text(
        text = text,
        modifier = modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.displayMedium
    )
}

@Composable
fun CounterButtonComposable(
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.i(LOG_TAG,  "CounterButtonComposable started with buttonText = $buttonText")

    FilledTonalButton(
        onClick = onButtonClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = buttonText,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium
        )
    }
}
