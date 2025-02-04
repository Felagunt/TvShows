package com.example.tvapp.data.remote.dto

data class TvShowDto(
    val id: Int,
    val image: Image,
    val language: String,
    val name: String,
    val status: String
)