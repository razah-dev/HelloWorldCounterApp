package com.example.counterapp.data.cloud

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CloudFileData (
    @SerialName(value = "file_id")
    val fileId: Int
)