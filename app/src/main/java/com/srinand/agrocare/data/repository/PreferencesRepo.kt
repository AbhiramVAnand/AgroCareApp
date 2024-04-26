package com.srinand.agrocare.data.repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesRepo @Inject constructor(
    private val appContext : Application
) {
    var sharedPreferences : SharedPreferences
    val USERNAME = "username"
    val PASSWORD = "password"
    val ISFIRST = "isfirst"
    val UID = "uid"
    val MOTORSTATUS = "motor"

    init {
        sharedPreferences = appContext.getSharedPreferences("my_app_prefs",Context.MODE_PRIVATE)
    }

    fun setUname(uname :String){
        sharedPreferences.edit().putString(USERNAME,uname).apply()
    }

    fun getUname() : String{
        return sharedPreferences.getString(USERNAME,"").toString()
    }

    fun setMotorStatus(status : Boolean) {
        sharedPreferences.edit().putBoolean(MOTORSTATUS,status).apply()
    }

    fun getMotorStatus() : Boolean{
        return sharedPreferences.getBoolean(MOTORSTATUS,false)
    }

    fun setPassword(password :String){
        sharedPreferences.edit().putString(PASSWORD,password).apply()
    }

    fun getPassword() : String{
        return sharedPreferences.getString(PASSWORD,"").toString()
    }

    fun setUID(uid : String){
        sharedPreferences.edit().putString(UID,uid).apply()
    }

    fun getUserID() : String{
        return sharedPreferences.getString(UID,"").toString()
    }

    fun getIsFirst() : Boolean {
        return sharedPreferences.getBoolean(ISFIRST,true)
    }

    fun setNotFirst(){
        sharedPreferences.edit().putBoolean(ISFIRST,false).apply()
    }

    fun setFirst() {
        sharedPreferences.edit().putBoolean(ISFIRST,true).apply()
    }

}