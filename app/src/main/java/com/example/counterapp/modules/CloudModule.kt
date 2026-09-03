package com.example.counterapp.modules

import com.example.counterapp.cloud.CloudApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

// TODO (raza): Change to remote (cloud) endpoint once
//  the external cloud application is deployed on cloud.
private const val BASE_URL = "http://10.0.2.2:8000" // "http://127.0.0.1:8000"

@Module
@InstallIn(SingletonComponent::class)
object CloudModule {
    @Provides
    @Singleton
    fun provideCloudApiService(
        // Potential dependencies of this type
    ): CloudApiService {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CloudApiService::class.java)
    }
}
