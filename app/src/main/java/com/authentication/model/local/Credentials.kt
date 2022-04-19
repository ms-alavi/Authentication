package com.authentication.model.local

data class Credentials(
    val refreshToken: String,
    val token: String
)