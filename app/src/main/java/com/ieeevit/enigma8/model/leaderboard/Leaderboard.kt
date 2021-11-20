package com.ieeevit.enigma8.model.leaderboard



data class Leaderboard (

	val username : String,
	val score : Float,
	val questionsSolved : Int,
	val rank : Int
)