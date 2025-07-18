package com.ecodeli.app.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val user: User?
)
