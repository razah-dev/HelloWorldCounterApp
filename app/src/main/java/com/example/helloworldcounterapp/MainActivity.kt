package com.example.helloworldcounterapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.helloworldcounterapp.ui.theme.HelloWorldCounterAppTheme

private val LOG_TAG: String = "MainActivityTag"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelloWorldCounterAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CounterScreen(
                        userName = "Raza Hussain",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CounterScreen(userName: String, modifier: Modifier = Modifier) {
    Log.i(LOG_TAG,  "Starting compose function Counter Screen")
    var counter: Int by remember { mutableStateOf(0) }
    Column {
        Text(
            text = "Greetings $userName!",
            modifier = modifier,
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = "Counter Value: $counter",
            modifier = modifier,
            style = MaterialTheme.typography.displayLarge
        )
        FilledTonalButton(
            onClick = {
                counter += 1
                Log.i(LOG_TAG, "Increment button clicked. Counter: $counter")
            }
        ) {
            Text(
                text = "Increment Count",
                modifier = modifier,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterScreenPreview() {
    HelloWorldCounterAppTheme {
        CounterScreen("Raza")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HelloWorldCounterAppTheme {
        Greeting("Android")
    }
}