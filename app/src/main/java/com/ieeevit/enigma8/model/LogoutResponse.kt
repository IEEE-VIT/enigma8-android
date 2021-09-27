package com.ieeevit.enigma8.model

import com.google.gson.annotations.SerializedName

data class LogoutResponse(

    @SerializedName("detail")
    val detail: String? = null
)