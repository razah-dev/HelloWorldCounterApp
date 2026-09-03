package com.example.counterapp.ui.cloudcontent

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.counterapp.ui.utils.CounterButtonComposable
import com.example.counterapp.ui.utils.CounterTextComposable

private const val LOG_TAG: String = "CloudScreen"

@Composable
fun CloudScreenContent(
    contentFetched: String,
    onContentFetch: () -> Unit,
    verticalArrangement: Arrangement.Vertical,
    horizontalAlignment: Alignment.Horizontal,
    modifier: Modifier = Modifier,
) {
    Log.i(LOG_TAG,  "CloudScreenContent started")

    Column (
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        if (contentFetched.isNotEmpty()) {
            CounterTextComposable(
                text = "Cloud content: $contentFetched"
            )
        }
        CounterButtonComposable(
            buttonText = "Cloud Content Fetch",
            onButtonClick = onContentFetch
        )
    }
}