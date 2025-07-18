package com.ecodeli.app.model

data class AnnonceTransport(
    val id: Int,
    val user_id: Int,
    val title: String,
    val description: String,
    val from_city: String,
    val to_city: String,
    val preferred_date: String,
    val price: String,
    val status: String
)