package com.ieeevit.enigma8.model.room


data class Room (

		val _id : String,
		val roomNo : String,
		val questionId : List<String>,
		val media : String,
		val title : String,
		val starQuota : Int
)