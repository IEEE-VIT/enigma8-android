package com.ieeevit.enigma8.model

import com.ieeevit.enigma8.model.Status

import com.google.gson.annotations.SerializedName

data class StatusResponse (

		val success : Boolean,
		val data : Status
)