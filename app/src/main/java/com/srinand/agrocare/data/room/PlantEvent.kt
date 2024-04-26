package com.srinand.agrocare.data.room


sealed interface PlantEvent {
    object AddPlant : PlantEvent
    data class SetName(val name : String) : PlantEvent
    data class SetMoisture(val moisture: String) : PlantEvent
    data class SetMotorRunning(val motorStatus : Boolean) : PlantEvent
    data class DeletePlant(val device : Plants) : PlantEvent
    object ShowDialog : PlantEvent
    object HideDialog : PlantEvent

}