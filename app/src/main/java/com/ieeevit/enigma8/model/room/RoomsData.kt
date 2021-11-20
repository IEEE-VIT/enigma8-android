package com.ieeevit.enigma8.model.room


data class RoomsData (
        val data : List<DaRoom>,
        val nextRoomsUnlockedIn:Int,
        val stars:Int
)