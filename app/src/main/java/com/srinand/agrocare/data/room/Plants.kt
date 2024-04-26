package com.srinand.agrocare.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize

@VersionedParcelize
@Entity()
data class Plants(

    @ColumnInfo(name = "name")
    var name : String,

    @ColumnInfo(name = "moisture")
    var moisture : String,

    @ColumnInfo(name = "isMotorRunning")
    var isMotorRunning : Boolean,

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0

//    @ColumnInfo(name = "water")
//    var water : Int
)
