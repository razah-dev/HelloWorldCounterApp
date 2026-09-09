package com.example.counterapp.data

interface CounterDataRepository {
    suspend fun saveCounterData(counterData: CounterData): CounterData

    suspend fun fetchCounterData(): List<CounterData>
}