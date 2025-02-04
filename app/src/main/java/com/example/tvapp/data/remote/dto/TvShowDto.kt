package com.example.tvapp.data.remote.dto

data class TvShowDto(
    val id: Int,
    val name: String,
    val language: String,
    val image: Image,
    val status: String
)