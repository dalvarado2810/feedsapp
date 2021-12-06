package com.dani.kibernum.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FavoriteResponse (
    @SerializedName("STATUS")val status : String,
    @SerializedName("message")val message : String?
): Serializable