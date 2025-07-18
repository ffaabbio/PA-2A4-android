package com.ecodeli.app.model

data class AnnoncePrestation(
    val id: Int,
    val user_id: Int,
    val title: String,
    val description: String,
    val preferred_date: String,
    val price: String,
    val status: String
)
