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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.counterapp.ui.fetchcontent.CloudContentViewModel
import com.example.counterapp.ui.fetchcontent.FetchContentScreen
import com.example.counterapp.ui.fetchcontent.FetchContentUiState
import com.example.counterapp.ui.fetchcontent.LocalContentViewModel
import com.example.counterapp.ui.fetchfiles.FetchFilesScreen
import com.example.counterapp.ui.fetchfiles.FetchFilesUiState
import com.example.counterapp.ui.fetchfiles.FetchFilesViewModel
import com.example.counterapp.ui.manualcounter.CounterDataUiEntry
import com.example.counterapp.ui.manualcounter.ManualCounterScreenContent
import com.example.counterapp.ui.manualcounter.ManualCounterViewModel
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
                        username = "Raza Hussain",
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
    username: String,
    modifier: Modifier = Modifier,
    manualCounterViewModel: ManualCounterViewModel = hiltViewModel(),
    autoCounterViewModel: AutoCounterViewModel = hiltViewModel(),
    cloudContentViewModel: CloudContentViewModel = hiltViewModel(),
    localContentViewModel: LocalContentViewModel = hiltViewModel(),
    fetchFilesViewModel: FetchFilesViewModel = hiltViewModel(),
) {
    Log.i(LOG_TAG,  "CounterScreen started")
    val manualCounterUiState: CounterDataUiEntry by manualCounterViewModel.uiState.collectAsStateWithLifecycle()
    val autoCounterUiState: AutoCounterUiState by autoCounterViewModel.uiState.collectAsStateWithLifecycle()
    val cloudContentUiState: FetchContentUiState by cloudContentViewModel.uiState.collectAsStateWithLifecycle()
    val localContentUiState: FetchContentUiState by localContentViewModel.uiState.collectAsStateWithLifecycle()
    val fetchFilesUiState: FetchFilesUiState by fetchFilesViewModel.uiState.collectAsStateWithLifecycle()

    // Set username
    if (manualCounterUiState.username.isEmpty()) {
        manualCounterViewModel.setUsername(username = username)
    }

    // Kick off timestamp update process (async)
    if (manualCounterUiState.timestampHumanReadable.isEmpty()) {
        manualCounterViewModel.timestampProcess()
    }

    Column (
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ManualCounterScreenContent(
            username = manualCounterUiState.username,
            timestampHumanReadable = manualCounterUiState.timestampHumanReadable,
            counterValue = manualCounterUiState.counterValue,
            onIncrement = { manualCounterViewModel.incrementCounter() },
            onReset = { manualCounterViewModel.resetCounter() },
            onSaveToCloud = { manualCounterViewModel.saveToCloudCounter() },
            onSaveToLocal = { manualCounterViewModel.saveToLocalCounter() },
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
        FetchContentScreen(
            textHeading = "Cloud DB API",
            isContentFetched = cloudContentUiState.isContentFetched,
            counterDataList = cloudContentUiState.counterDataList,
            onContentFetch = { cloudContentViewModel.fetchCounterData() },
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier,
        )
        FetchContentScreen(
            textHeading = "Local DB API",
            isContentFetched = localContentUiState.isContentFetched,
            counterDataList = localContentUiState.counterDataList,
            onContentFetch = { localContentViewModel.fetchCounterData() },
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier,
        )
        FetchFilesScreen(
            textHeading = "Cloud Fetch / Download Files API",
            isFetchedFileIdsForDevice = fetchFilesUiState.isFetchedFileIdsForDevice,
            allFileIdsForDevice = fetchFilesUiState.allFileIdsForDevice,
            onButtonClickFetchFileIds = { fetchFilesViewModel.fetchAllFileIdsDataList() },
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ManualCounterScreenContentPreview() {
    HelloWorldCounterAppTheme {
        ManualCounterScreenContent(
            username = "Raza",
            timestampHumanReadable = "2026/09/05 02:00 UTC",
            counterValue = 120,
            onIncrement = { },
            onReset = { },
            onSaveToCloud = { },
            onSaveToLocal = { },
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
