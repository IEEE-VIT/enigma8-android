package com.ieeevit.enigma8.model


data class Room (

	val _id : String,
	val roomNo : Int,
	val questionId : List<String>,
	val media : String,
	val title : String,
	val starQuota : Int
)