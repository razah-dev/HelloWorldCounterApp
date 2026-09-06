package com.example.counterapp.ui.cloudcontent

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.counterapp.ui.utils.CounterButtonComposable
import com.example.counterapp.ui.utils.CounterTextComposable

private const val LOG_TAG: String = "CloudScreen"

@Composable
private fun ScrollableList(
    counterDataUiEntryList: List<CounterDataUiEntry>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.LightGray)
            .size(400.dp)
            .verticalScroll(rememberScrollState())
    ) {
        repeat(times = counterDataUiEntryList.size) { index ->
            val counterDataUiEntry: CounterDataUiEntry = counterDataUiEntryList[index]
            Text(
                text = "${index + 1}) Counter = ${counterDataUiEntry.counterValue}\n${counterDataUiEntry.username}\n${counterDataUiEntry.timestampHumanReadable}",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

@Composable
fun CloudScreenContent(
    isContentFetched: Boolean,
    counterDataUiEntryList: List<CounterDataUiEntry>,
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
        CounterTextComposable(
            text = "Cloud Content Fetch API"
        )
        if (isContentFetched) {
            CounterTextComposable(
                text = "Fetched ${counterDataUiEntryList.size} items"
            )
            ScrollableList(
                counterDataUiEntryList = counterDataUiEntryList,
                modifier = modifier
            )
        }
        CounterButtonComposable(
            buttonText = "Fetch Counter Data List",
            onButtonClick = onContentFetch
        )
    }
}
