package com.srinand.agrocare.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hivemq.client.mqtt.MqttClientState
import com.hivemq.client.mqtt.MqttGlobalPublishFilter
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
import com.srinand.agrocare.data.repository.PreferencesRepo
import com.srinand.agrocare.data.room.AlertEvent
import com.srinand.agrocare.data.room.AlertState
import com.srinand.agrocare.data.room.Alerts
import com.srinand.agrocare.data.room.AppDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MQTTClientViewModel @Inject constructor(
    appContext : Application,
    preferenceRepo : PreferencesRepo,
    private val appDao: AppDao
) : ViewModel() {
    val wsUri = "7509b34df87c47ad8a934836f0109ec8.s2.eu.hivemq.cloud" // Replace with your WebSocket address
    val uid = preferenceRepo.getUserID()
    val sharedPreferences = preferenceRepo// Replace with your HiveMQ password (optional)
    val client = Mqtt5Client.builder()
        .identifier(uid)
        .serverHost(wsUri)
        .serverPort(8883)
        .sslWithDefaultConfig()
        .buildBlocking()

    var selectedDevice by mutableStateOf("")
    var showDeviceSheet by mutableStateOf(false)

    var moisture by mutableStateOf("")
    var temperature by mutableStateOf("")
    var humidity by mutableStateOf("")
    var waterlevel by mutableStateOf("")

    var isToggled by mutableStateOf(false)
    var motor by mutableStateOf(true)

    private val _alerts = appDao.getAlerts()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    val _alertState = MutableStateFlow(AlertState())

    val alertState = combine(_alertState, _alerts){ state, alert ->
        state.copy(
            alerts = alert
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AlertState())

    fun onAlertEvent(event: AlertEvent){
        when(event){
            AlertEvent.AddAlert -> {
                val msg = alertState.value.msg
                val time = alertState.value.time

                if (msg.isBlank()){
                    Log.e("AlertAddng",msg+" Blank")
                    return
                }
                val alert = Alerts(
                    message = msg,
                    time = time
                )
                Log.e("AlertAddng",msg+" "+time)
                viewModelScope.launch {
                    appDao.addAlert(alert)
                }
            }
            is AlertEvent.DeleteAlert -> {
                viewModelScope.launch {
                    appDao.deleteAlert(event.msg)
                }
            }
            is AlertEvent.SetMsg -> {
                _alertState.update { it.copy(
                    msg = event.msg
                ) }
            }
            is AlertEvent.SetTime -> {
                _alertState.update { it.copy(
                    msg = event.time
                ) }
            }
        }
    }

    fun getUname() : String{
        return sharedPreferences.getUname()
    }

    fun setIsNotFirst(){
        sharedPreferences.setNotFirst()
    }

    fun getIsFirst(): Boolean {
        return sharedPreferences.getIsFirst()
    }

    fun Connect(username : String, password : String): Boolean {
        sharedPreferences.setUname(username)
        sharedPreferences.setPassword(password)
        var status : Boolean = false
        try {
            client.connectWith()
                .simpleAuth()
                .username(username)
                .password(StandardCharsets.UTF_8.encode(password))
                .applySimpleAuth()
                .send()


            Log.e("OnConnect","Success ${client.state}")
            status = true
        }catch (e : Exception){
            Log.e("OnConnect",e.message.toString())
        }
        return status
    }

    fun UnSubscribeToPlant(name : String){
        try {
            if (client.state==MqttClientState.CONNECTED){
                client.unsubscribeWith()
                    .topicFilter("$name/moisture")
                    .send()
            }else{
                client.connectWith()
                    .simpleAuth()
                    .username("nodeesp")
                    .password(StandardCharsets.UTF_8.encode("Password01"))
                    .applySimpleAuth()
                    .send()
                client.unsubscribeWith()
                    .topicFilter("$name/moisture")
                    .send()
            }
        } catch (e : Exception){
            Log.e("Failed","Failed")
        }
    }

    fun Logout(){
        sharedPreferences.setUname("")
        sharedPreferences.setPassword("")
        sharedPreferences.setFirst()
    }
    fun SubscribeToAlert(){
        val uname = sharedPreferences.getUname()
        val password = sharedPreferences.getPassword()
        try {
            if (client.state==MqttClientState.CONNECTED){
                client.subscribeWith()
                    .topicFilter("alert/deviceMotor")
                    .send()
                client.toAsync().publishes(MqttGlobalPublishFilter.ALL) { publish: Mqtt5Publish ->
                    if (publish.topic.toString() == "alert/deviceMotor"){
                        val currentTimeMillis = System.currentTimeMillis()
                        val currentDate = Date(currentTimeMillis)
                        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        val formattedDate = formatter.format(currentDate)
                        val alert = Alerts(
                            message = StandardCharsets.UTF_8.decode(publish.payload.get()).toString(),
                            time = formattedDate
                        )
                        viewModelScope.launch {
                            appDao.addAlert(alert)
                        }

                    }
                }
            }else{
                client.connectWith()
                    .simpleAuth()
                    .username(uname)
                    .password(StandardCharsets.UTF_8.encode(password))
                    .applySimpleAuth()
                    .send()
                client.subscribeWith()
                    .topicFilter("alert/deviceMotor")
                    .send()
                client.toAsync().publishes(MqttGlobalPublishFilter.ALL) { publish: Mqtt5Publish ->
                    if (publish.topic.toString() == "alert/deviceMotor"){
                        val currentTimeMillis = System.currentTimeMillis()
                        val currentDate = Date(currentTimeMillis)
                        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        val formattedDate = formatter.format(currentDate)
                        val alert = Alerts(
                            message = StandardCharsets.UTF_8.decode(publish.payload.get()).toString(),
                            time = formattedDate
                        )
                        viewModelScope.launch {
                            appDao.addAlert(alert)
                        }
                    }
                }
            }

        } catch (e : Exception){
            Log.e("Failed",e.message.toString()+uname+password)
        }
    }

    fun SubscribeToPlant(name : String): MutableStateFlow<String?> {
        val uname = sharedPreferences.getUname()
        val password = sharedPreferences.getPassword()
        var moisture = MutableStateFlow<String?>("")
        viewModelScope.launch {
            motor = appDao.getIsMotorRunning(name)
        }
        try {
            if (client.state==MqttClientState.CONNECTED){
                client.subscribeWith()
                    .topicFilter("$name/moisture")
                    .send()
                client.toAsync().publishes(MqttGlobalPublishFilter.ALL) { publish: Mqtt5Publish ->
                    when(publish.topic.toString()){
                        "$name/moisture" -> moisture.value = StandardCharsets.UTF_8.decode(publish.payload.get()).toString()
                    }
                }
            }else{
                client.connectWith()
                    .simpleAuth()
                    .username(uname)
                    .password(StandardCharsets.UTF_8.encode(password))
                    .applySimpleAuth()
                    .send()
                client.subscribeWith()
                    .topicFilter("$name/moisture")
                    .send()
                client.toAsync().publishes(MqttGlobalPublishFilter.ALL) { publish: Mqtt5Publish ->
                    when(publish.topic.toString()){
                        "$name/moisture" -> moisture.value = StandardCharsets.UTF_8.decode(publish.payload.get()).toString()
                    }
                }
            }

        } catch (e : Exception){
            Log.e("Failed","Failed")
        }
        return moisture
    }

    fun DeviceOn(device : String, status : Boolean ){
        motor = !motor
        if (status){
            Log.e("Motor","on")
            motor = !motor
            client.publishWith()
                .topic("$device/motor")
                .payload("1".toByteArray())
                .send()
        }else{
            Log.e("Motor","off")
            client.publishWith()
                .topic("$device/motor")
                .payload("0".toByteArray())
                .send()
        }
    }

    fun SetMotorStatus(name: String) {
        motor = !motor
        viewModelScope.launch {
            appDao.updateIsMotorRunning(name,motor)
        }
        if (motor){
            client.publishWith()
                .topic("$name/motor")
                .payload("1".toByteArray())
                .send()
        }else{
            client.publishWith()
                .topic("$name/motor")
                .payload("0".toByteArray())
                .send()
        }
    }
}