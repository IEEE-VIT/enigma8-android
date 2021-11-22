package com.ieeevit.enigma8.model.room

import android.provider.MediaStore


data class RoomsOuter (
        val name: String,
        val media: String,
        val leftLamp: Int,
        val centerLamp: Int,
        val rightLamp: Int,
        val left: Int,
        val center: Int,
        val right: Int,
        val roomid: String,
        val roomNo:String,
        val unlock:Boolean,
        var powerupUsed:String,
        var starQuata:Int,
        var questionList: List<String>,
        var powerupSet:String?,
        val starLeft:Int


)
