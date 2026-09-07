package com.example.counterapp.modules

import com.example.counterapp.data.DataRepository
import com.example.counterapp.data.RemoteDataRepository
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
    @RemoteRepository
    abstract fun bindRemoteUserRepository(
        remoteDataRepository: RemoteDataRepository
    ): DataRepository
}