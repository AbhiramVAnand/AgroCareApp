package com.srinand.agrocare.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srinand.agrocare.data.room.AppDao
import com.srinand.agrocare.data.room.PlantEvent
import com.srinand.agrocare.data.room.PlantState
import com.srinand.agrocare.data.room.Plants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor (
    private val appDao: AppDao
) : ViewModel(){


    private val _plants = appDao.getPlants()
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    val _state = MutableStateFlow(PlantState())

    val state = combine(_state, _plants){ state, plants ->
        state.copy(
            plants = plants
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PlantState())

    fun onEvent(event : PlantEvent){
        when(event){
            PlantEvent.AddPlant -> {
                val name = state.value.name
                val plantName = state.value.plantName
                val moisture = state.value.moisture
                val motorState = state.value.isMotorRunning

                if (name.isBlank()){
                    return
                }

                val plants = Plants(
                    name = name,
                    moisture = moisture,
                    isMotorRunning = motorState
                )

                viewModelScope.launch {
                    appDao.addDevice(plants)
                }
                _state.update {it.copy(
                    isAddingPlant = false
                ) }
            }

            PlantEvent.HideDialog -> {
                _state.update {it.copy(
                    isAddingPlant = false
                ) }
            }

            is PlantEvent.SetMoisture -> {
                _state.update { it.copy(
                  moisture = event.moisture
                ) }
            }

            is PlantEvent.SetMotorRunning -> {
                _state.update { it.copy(
                    isMotorRunning = event.motorStatus
                ) }
            }
            is PlantEvent.SetName -> {
                _state.update{it.copy(
                    name = event.name
                )}
            }
            PlantEvent.ShowDialog -> {
                _state.update {it.copy(
                    isAddingPlant = true
                ) }
            }

            is PlantEvent.DeletePlant -> {
                viewModelScope.launch {
                    appDao.delete(event.device)
                }
            }
        }
    }
}