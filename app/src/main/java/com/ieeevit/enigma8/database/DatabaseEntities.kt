package com.ieeevit.enigma8.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_details")
data class UserDetails constructor(
        @PrimaryKey
        val username: String?

)