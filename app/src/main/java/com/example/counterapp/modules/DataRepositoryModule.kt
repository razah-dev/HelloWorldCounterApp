package com.example.counterapp.modules

import com.example.counterapp.data.CounterDataRepository
import com.example.counterapp.data.cloud.CloudCounterDataRepository
import com.example.counterapp.data.local.LocalCounterDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataRepositoryModule {
    @Binds
    @Singleton
    @CloudDataRepository
    abstract fun bindCloudDataRepository(
        cloudCounterDataRepository: CloudCounterDataRepository
    ): CounterDataRepository

    @Binds
    @Singleton
    @LocalDataRepository
    abstract fun bindLocalDataRepository(
        localCounterDataRepository: LocalCounterDataRepository
    ): CounterDataRepository
}