package com.srinand.agrocare.data.room

data class PlantState(
    val plants: List<Plants> = emptyList(),
    var name: String = "",
    var plantName: String = "",
    var moisture: String = "",
    val isMotorRunning: Boolean = false,
    val isAddingPlant: Boolean = false
)
