package com.example.counterapp.data.local

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "local_counter_data")
data class LocalCounterData(
    @PrimaryKey(autoGenerate = true)
    val recordId: Int = 0,

    val username: String,

    @ColumnInfo(name = "timestamp_8601_ISO_format")
    val timestamp8601ISOFormat: String,

    @ColumnInfo(name = "counter_value")
    val counterValue: Int
)
