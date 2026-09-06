package com.example.counterapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CounterData(
    val username: String,
    @SerialName(value = "timestamp_8601_ISO_format")
    val timestamp8601ISOFormat: String,
    @SerialName(value = "counter_value")
    val counterValue: Int
)
