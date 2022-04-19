package com.authentication.model.local

data class User(
    val address: String,
    val firstName: String,
    val image: String,
    val lastName: String,
    val phone: String,
    val uuid: String
)