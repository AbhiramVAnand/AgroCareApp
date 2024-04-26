package com.srinand.agrocare.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Alerts (
    val message : String,
    @PrimaryKey(autoGenerate = false)
    val time : String

)