package com.srinand.agrocare.data.room

sealed interface AlertEvent {
    object AddAlert : AlertEvent

    data class SetMsg(val msg : String) : AlertEvent

    data class SetTime(val time : String) : AlertEvent

    data class DeleteAlert(val msg : String) : AlertEvent

}