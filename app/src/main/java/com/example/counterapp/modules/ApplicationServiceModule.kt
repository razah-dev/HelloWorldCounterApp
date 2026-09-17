package com.example.counterapp.modules

import android.content.ContentResolver
import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.example.counterapp.data.cloud.CloudCounterDataApiService
import com.example.counterapp.data.local.LocalCounterDataDAO
import com.example.counterapp.data.local.LocalRoomDatabase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// TODO (raza): Change to remote (cloud) endpoint once
//  the external cloud application is deployed on cloud.
//  Localhost ("http://127.0.0.1:8000") as seen from android
//  emulator is the address "http://10.0.2.2:8000".
private const val CLOUD_COUNTER_DATA_SERVER_BASE_URL = "http://10.0.2.2:8000"

@Module
@InstallIn(SingletonComponent::class)
object ApplicationServiceModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideCloudCounterDataApiService(
        okHttpClient: OkHttpClient
    ): CloudCounterDataApiService {
        return Retrofit.Builder()
            .baseUrl(CLOUD_COUNTER_DATA_SERVER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json; charset=utf-8".toMediaType()))
            .build()
            .create(CloudCounterDataApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideContentResolver(
        @ApplicationContext context: Context
    ): ContentResolver {
        return context.contentResolver
    }


    @Provides
    @Singleton
    fun provideLocalRoomDatabase(
        @ApplicationContext context: Context
    ): LocalRoomDatabase {
        return Room.databaseBuilder<LocalRoomDatabase>(
            context = context,
            name = "my-counterapp-db")
            .setDriver(AndroidSQLiteDriver())
            .build()
    }

    @Provides
    @Singleton
    fun provideLocalCounterDataDAO(
        localRoomDatabase: LocalRoomDatabase
    ): LocalCounterDataDAO {
        return localRoomDatabase.localCounterDataDAO()
    }
}
