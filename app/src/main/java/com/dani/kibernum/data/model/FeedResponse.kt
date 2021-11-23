package com.dani.kibernum.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FeedResponse (
    @SerializedName("ID") val id: String,
@SerializedName("status") val status: String
) : Serializable