package com.dani.kibernum.data.model

import java.io.Serializable

data class LoginDTO(
    val username: String,
    val password: String
): Serializable