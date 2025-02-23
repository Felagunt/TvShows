package com.example.tvapp.domain.models


data class Episode(
    val airdate: String,
    val airstamp: String,
    val airtime: String,
    val id: Int,
    val image: String,
    val name: String,
    val number: Int,
    val rating: Double,
    val runtime: Int,
    val season: Int,
    val summary: String,
    val type: String,
    val url: String
)