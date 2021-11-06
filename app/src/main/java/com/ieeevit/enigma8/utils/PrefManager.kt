package com.ieeevit.enigma8.utils

import android.content.Context
import android.content.SharedPreferences
import com.ieeevit.enigma8.viewModel.RoomViewModel

class PrefManager(val context: Context) {
    private val prefName = "com.ieeevit.enigma8"
    private val sharedPref: SharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    private val authorizationCode: String = "AuthorizationCode"
    private val username: String = "Username"
    private val userNameExist: String = "userStatus"
    private val hint: String = "hintString"
    private val isFirstTimeLaunch = "IsFirstTimeLaunch"
    private val gameStarted: String = "IsGameStarted"
    private val xP = "xP"
    private val editor: SharedPreferences.Editor = sharedPref.edit()
    private val loggedIN = "IsLoggedIn"
    private val enigmaStatus = "EnigmaStatus"
    private val canShowHintDialog = "CanShowHintDialog"
    private val roomId : String = "RoomID"
    private val powerupId : String = "PowerupID"


    fun setAuthCode(text: String) {
        editor.putString(authorizationCode, text)
        editor.apply()
    }
    fun setRoomid(text: String) {
        editor.putString(roomId,text)
        editor.apply()
    }

    fun setPowerupid(text: String) {
        editor.putString(powerupId,text)
        editor.apply()
    }


//    fun setUserStatus(text: Boolean) {
//        editor.putBoolean(userNameExist, text)
//        editor.apply()
//    }
//
//    fun setFirstTimeInstruction(text: Boolean) {
//        editor.putBoolean(isFirstTimeLaunch, text)
//        editor.apply()
//    }
//
//    fun isFirstTimeInstruction(): Boolean {
//        return sharedPref.getBoolean(isFirstTimeLaunch, true)
//    }
//
    fun setHuntStarted(text: Boolean) {
        editor.putBoolean(gameStarted, text)
        editor.apply()
    }

    fun isHuntStarted(): Boolean {
        return sharedPref.getBoolean(gameStarted, false)
    }
//
//    fun setIsLoggedIn(text: Boolean) {
//        editor.putBoolean(loggedIN, text)
//        editor.apply()
//    }
//
//    fun isLoggedIn(): Boolean {
//        return sharedPref.getBoolean(loggedIN, false)
//    }

    fun getAuthCode(): String? {
        return sharedPref.getString(authorizationCode, null)
    }

    fun getRoomid(): String? {
        return sharedPref.getString(roomId,null)
    }
    fun getPowerupid(): String? {
        return sharedPref.getString(powerupId,null)
    }



    fun getUserStatus(): Boolean? {
        return sharedPref.getBoolean(userNameExist, false)
    }
//
//    fun setHint(text: String?) {
//        editor.putString(hint, text)
//        editor.apply()
//    }
//
//    fun getHintString(): String? {
//        return sharedPref.getString(hint, null)
//    }
//
//    fun setQuestionFlag(boolean: Boolean){
//        editor.putBoolean("Question Flag", boolean)
//        editor.apply()
//    }
//
//    fun getQuestionFlag(): Boolean{
//        return sharedPref.getBoolean("Question Flag", false)
//    }


    fun clearSharedPreference() {
        editor.clear()
        editor.apply()
    }

    fun setXp(text: Int) {
        editor.putInt(xP, text)
        editor.apply()
    }

    fun getXp(): Int {
        return sharedPref.getInt(xP, 0)
    }

    fun setEnigmaStatus(text: Boolean) {
        editor.putBoolean(enigmaStatus, text)
        editor.apply()
    }

    fun getEnigmaStatus(): Boolean {
        return sharedPref.getBoolean(enigmaStatus, false)
    }
//
//    fun setIsShowHintDialog(text: Boolean) {
//        editor.putBoolean(canShowHintDialog, text)
//        editor.apply()
//    }

//    fun isShowHintDialog(): Boolean {
//        return sharedPref.getBoolean(canShowHintDialog, false)
//    }

    fun setUsername(text: String?) {
        editor.putString(username, text)
        editor.apply()
    }

    fun getUsername(): String? {
        return sharedPref.getString(username, null)
    }

}

