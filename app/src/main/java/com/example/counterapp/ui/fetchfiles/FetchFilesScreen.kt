package com.example.counterapp.ui.fetchfiles

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

@Composable
private fun ScrollableList(
    listElements: List<Int>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.LightGray)
            .size(400.dp)
            .verticalScroll(rememberScrollState())
    ) {
        repeat(times = listElements.size) { index ->
            val listElement: Int = listElements[index]
            Text(
                text = "$listElement",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

@Composable
fun FetchFilesScreen(
    textHeading: String,
    isFetchedFileIdsForDevice: Boolean,
    allFileIdsForDevice: List<Int>,
    onButtonClickFetchFileIds: () -> Unit,
    verticalArrangement: Arrangement.Vertical,
    horizontalAlignment: Alignment.Horizontal,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        CounterTextComposable(
            text = textHeading
        )
        CounterButtonComposable(
            buttonText = "Fetch File Ids Data",
            onButtonClick = onButtonClickFetchFileIds
        )
        if (isFetchedFileIdsForDevice) {
            CounterTextComposable(
                text = "Fetched ${allFileIdsForDevice.size} items"
            )
            ScrollableList(
                listElements = allFileIdsForDevice,
                modifier = modifier
            )
        }
    }
}