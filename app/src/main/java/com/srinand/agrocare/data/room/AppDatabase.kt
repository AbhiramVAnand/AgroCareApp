package com.srinand.agrocare.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [(Plants::class), (Alerts::class)],
    version = 5
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao() : AppDao

}