package com.example.counterapp.data.local

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query

@Dao
interface LocalCounterDataDAO {
    @Insert
    suspend fun insert(localCounterData: LocalCounterData)

    @Delete
    suspend fun delete(localCounterData: LocalCounterData)

    @Query("select * from local_counter_data limit 1")
    suspend fun fetch(): LocalCounterData

    @Query("select * from local_counter_data")
    suspend fun fetchAllRows(): List<LocalCounterData>
}