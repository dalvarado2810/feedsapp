package com.dani.kibernum.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse(
    @SerializedName("STATUS") val status: String,
    @SerializedName("api-token") val apiToket: String
): Serializable