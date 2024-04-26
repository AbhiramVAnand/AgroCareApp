package com.srinand.agrocare.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Upsert
    suspend fun addDevice(plants : Plants)

    @Query("SELECT * FROM plants")
    fun getPlants() : Flow<List<Plants>>

    @Upsert
    suspend fun addAlert(alert : Alerts)

    @Query("SELECT * FROM alerts ORDER BY time DESC")
    fun getAlerts() : Flow<List<Alerts>>

    @Query("SELECT isMotorRunning FROM plants WHERE name LIKE :deviceName")
    suspend fun getIsMotorRunning(deviceName: String) : Boolean

    @Query("UPDATE plants SET isMotorRunning = :status WHERE name LIKE :deviceName")
    suspend fun updateIsMotorRunning(deviceName: String, status : Boolean)

    @Query("DELETE FROM alerts WHERE message LIKE :msg")
    fun deleteAlert(msg: String)

    @Delete
    fun delete(device : Plants)

}