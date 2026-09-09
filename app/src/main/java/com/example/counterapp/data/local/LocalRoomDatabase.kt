package com.example.counterapp.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(entities = [LocalCounterData::class], version = 1)
abstract class LocalRoomDatabase : RoomDatabase() {
    abstract fun localCounterDataDAO(): LocalCounterDataDAO
}