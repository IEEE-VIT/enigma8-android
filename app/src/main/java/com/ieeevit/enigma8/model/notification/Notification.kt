package com.ieeevit.enigma8.model.notification


data class Notification (

	val _id : String,
	val text : String,
	val type : String,
	val metadata : String?,
	val timestamp : String
)