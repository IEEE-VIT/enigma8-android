package com.ieeevit.enigma8.model.question


data class SubmitData(

	val correctAnswer : Boolean,
	val closeAnswer : Boolean,
	val scoreEarned : Int,
	val nextRoomUnlocked : Boolean,
	val nextRoomId : String,
	val questionId : String
)