package com.ieeevit.enigma8.model.feedback

data class FeedbackRequest (

		val isVITStudent : Boolean,
		val regNo : String?,
		val vitEmail: String?,
		val gameRating:Int,
		val userExperience : String,
		val featureIdeas : String
)