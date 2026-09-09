package com.example.counterapp.modules

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CloudDataRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataRepository