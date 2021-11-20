package com.ieeevit.enigma8.model.question


data class SubmitResponse (

        val success : Boolean,
        val data : SubmitData?,
        val message: String?
)