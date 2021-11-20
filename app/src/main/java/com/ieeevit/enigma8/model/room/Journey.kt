package com.ieeevit.enigma8.model.room



data class Journey (

		val _id : String,
		val userId : String,
		val roomId : String,
		val roomUnlocked : Boolean,
		val questionsStatus : List<String>,
		val powerupId : String,
		val powerupUsed : String,
		val stars : Int,
		val powerupSet : String,
		val __v : Int
)