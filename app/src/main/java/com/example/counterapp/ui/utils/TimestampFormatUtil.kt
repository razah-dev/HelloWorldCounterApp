package com.example.counterapp.ui.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")

fun shortHumanReadableTimestamp(iso8601Timestamp: String): String {
    val zonedDateTime: ZonedDateTime = Instant
        .parse(iso8601Timestamp)
        .atZone(ZoneId.of("Z"))  // UTC Time Zone

    return "${zonedDateTime.format(DATE_TIME_FORMATTER)} UTC"
}
