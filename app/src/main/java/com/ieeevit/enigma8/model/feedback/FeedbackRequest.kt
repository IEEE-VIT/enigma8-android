package com.ieeevit.enigma8.model.feedback

data class FeedbackRequest (

	val isVITStudent : Boolean,
	val gameRating : Int,
	val userExperience : String,
	val featureIdeas : String,
	val difficulties : String,
	val other : String
)