package com.ieeevit.enigma8.model



data class Journey (

	val _id : String,
	val userId : String,
	val roomId : String,
	val stars : Int,
	val powerupUsed : Boolean,
	val roomUnlocked : Boolean,
	val powerupId : String,
	val questionsStatus : List<String>
)