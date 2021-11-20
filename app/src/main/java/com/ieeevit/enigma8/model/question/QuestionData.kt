package com.ieeevit.enigma8.model.question


data class QuestionData (

        val question : Question,
        val powerupDetails : PowerupDetails,
        val powerupUsed : String,
        val hint : String
)