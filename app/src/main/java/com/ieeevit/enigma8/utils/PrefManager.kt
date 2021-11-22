package com.ieeevit.enigma8.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ieeevit.enigma8.model.question.QuestionList
import com.ieeevit.enigma8.model.question.QustionStatus


class PrefManager(val context: Context) {

    private val prefName = "com.ieeevit.enigma8"
    private val sharedPref: SharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    private val authorizationCode: String = "AuthorizationCode"
    private val username: String = "Username"
    private val userNameExist: String = "userStatus"
    private val powerupUsed : String = "powerupStatus"
    private val hint: String = "hintString"
    private val count: String = "Count"
    private val isFirstTimeLaunch = "IsFirstTimeLaunch"
    private val gameStarted: String = "IsGameStarted"
    private val fcmToken:String = "FcmToken"
    private val powerupName:String = "powerupname"
    private val xP = "xP"
    private val editor: SharedPreferences.Editor = sharedPref.edit()
    private val loggedIN = "IsLoggedIn"
    private val enigmaStatus = "EnigmaStatus"
    private val roomOneid = "roomone"
    private val canShowHintDialog = "CanShowHintDialog"
    private val unlockstar: String = "unlockStar"
    private val roomId : String = "RoomID"
    private val powerupId : String = "PowerupID"
    private val roomNo = "RoomNo"
    private val outreach = "Outreach"
    private val notification = "Notification"
    private val stars : String = "stars"
    private val powerupSet = "powerupSet"
    private val onBoardingincdicator = "indicator"
    private val isNew = "isNew"
    private val instruction = "instruction"
    private val backIndicator = "backindicator"

    fun setInstruction(text: Int) {
        editor.putInt(instruction,text)
        editor.commit()
    }
    fun setBackIndicator(text: Int) {
        editor.putInt(backIndicator,text)
        editor.commit()
    }
    fun getBackIndicator ():Int {
        return  sharedPref.getInt(backIndicator,0)

    }


    fun setQuestionList(list:MutableList<QuestionList>){
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("LIST",json)
        editor.commit()
    }
    fun setIndicator(text: Int) {
        editor.putInt(onBoardingincdicator,text)
        editor.commit()
    }
    fun getJourneyList():MutableList<QustionStatus>{
        val gson = Gson()
        val json = sharedPref.getString("LIS",null)
        val type = object : TypeToken<MutableList<QustionStatus>>(){}.type//converting the json to list
        return gson.fromJson(json,type)//returning the list
    }

    fun setJourneyList(list:MutableList<QustionStatus>){
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("LIS",json)
        editor.commit()
    }
    fun setPowerupSetStatus(text:Boolean) {
        editor.putBoolean(powerupSet,text)
        editor.commit()
    }
    fun setStars(text: Int){
        editor.putInt(stars, text)
        editor.commit()
    }
    fun getStars(): Int? {
        return sharedPref.getInt(stars, 0)
    }
    fun setFcm(text: String) {
        editor.putString(fcmToken,text)
        editor.commit()
    }
    fun setPowerupName(text: String) {
        editor.putString(powerupName,text)
        editor.commit()
    }


    fun setNotification(text:String) {
        editor.putString(notification,text)
        editor.commit()
    }
    fun getQuestionList():MutableList<QuestionList>{
        val gson = Gson()
        val json = sharedPref.getString("LIST",null)
        val type = object :TypeToken<MutableList<QuestionList>>(){}.type//converting the json to list
        return gson.fromJson(json,type)//returning the list
    }



    fun setAuthCode(text: String) {
        editor.putString(authorizationCode, text)
        editor.commit()
    }
    fun setOutreach(text: String) {
        editor.putString(outreach,text)
        editor.commit()
    }

    fun setRoomNo(text: String) {
        editor.putString(roomNo, text)
        editor.commit()
    }
    fun setStarNeeded(text: Int) {
        editor.putInt(unlockstar, text)
        editor.commit()
    }


    fun setRoomid(text: String) {
        editor.putString(roomId, text)
        editor.commit()
    }


    fun setPowerupid(text: String) {
        editor.putString(powerupId, text)
        editor.commit()
    }
    fun setPowerup(text: String) {
        editor.putString(powerupUsed, text)
        editor.commit()
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
        editor.commit()
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
    fun getInstruction(): Int {
        return sharedPref.getInt(instruction,0)
    }
    fun getOutreach(): String? {
        return sharedPref.getString(authorizationCode,null)
    }
    fun getPowerupName()  : String? {
        return sharedPref.getString(powerupName, null)
    }
    fun getFcm():String? {
        return sharedPref.getString(fcmToken,null)
    }
    fun setRoomOneId(text: String) {
        editor.putString(roomOneid,text)
        editor.commit()
    }
    fun getRoomOneid():String? {
        return sharedPref.getString(roomOneid,null)
    }

    fun getRoomid(): String? {
        return sharedPref.getString(roomId, null)
    }
    fun getNotification(): String? {
        return sharedPref.getString(notification,null)
    }
    fun getPowerupSetStatus(): Boolean? {
        return sharedPref.getBoolean(powerupSet,false)
    }
    fun getRoomNo(): String? {
        return sharedPref.getString(roomNo, null)
    }
    fun getStarNeeded(): Int {
        return sharedPref.getInt(unlockstar, 0)
    }
    fun getPowerupid(): String? {
        return sharedPref.getString(powerupId, null)
    }



    fun getUserStatus(): Boolean? {
        return sharedPref.getBoolean(userNameExist, false)
    }
    fun getPowerup(): String? {
        return sharedPref.getString(powerupUsed, null)

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
//        editor.putBoolean("com.ieeevit.enigma8.model.question.Question Flag", boolean)
//        editor.apply()
//    }
//
//    fun getQuestionFlag(): Boolean{
//        return sharedPref.getBoolean("com.ieeevit.enigma8.model.question.Question Flag", false)
//    }


    fun clearSharedPreference() {
        editor.clear()
        editor.apply()
    }
    fun setCount(text: Int) {
        editor.putInt(count,text)
        editor.commit()
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
        editor.commit()
    }
    fun setisNew(text: Int) {
        editor.putInt(isNew,text)
        editor.commit()
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
        editor.commit()
    }

    fun getUsername(): String? {
        return sharedPref.getString(username, null)
    }
    fun getCount(): Int {
        return sharedPref.getInt(count,0)
    }
    fun getisNew():Int {
        return sharedPref.getInt(isNew,0)
    }
    fun getIndicator():Int {
        return sharedPref.getInt(onBoardingincdicator,0)
    }

}

