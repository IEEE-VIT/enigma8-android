package com.ieeevit.enigma8.model.leaderboard

data class LeaderboardData (

	val page : Int?,
	val userRank : Leaderboard?,
	val leaderboard : List<Leaderboard>,
	val totalPage : Int?
)