package com.example.counterapp.modules

import com.example.counterapp.cloud.CloudApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton
import kotlinx.serialization.json.Json

// TODO (raza): Change to remote (cloud) endpoint once
//  the external cloud application is deployed on cloud.
// Localhost ("http://127.0.0.1:8000") as seen from android emulator
// is the address "http://10.0.2.2:8000".
private const val BASE_URL = "http://10.0.2.2:8000"

@Module
@InstallIn(SingletonComponent::class)
object CloudModule {
    @Provides
    @Singleton
    fun provideCloudApiService(
        // Potential dependencies of this type
    ): CloudApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json; charset=utf-8".toMediaType()))
            .build()
            .create(CloudApiService::class.java)
    }
}
