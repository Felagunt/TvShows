package com.example.tvapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TvShowDetailEntity (
    @PrimaryKey(autoGenerate = false) val id: Int,
    val ended: String,
    val imdb: String,
    val thetvdb: Int,
    val tvrage: Int,
    val genres: List<String>,
    //val image: Image,
    val name: String,
    val image: String,
    val originalImg: String,
    val language: String,
    val officialSite: String,
    val premiered: String,
    val rating: Double,
    val runtime: Int,
    //val schedule: Schedule,
    val status: String,
    val summary: String,
    val type: String,
    val url: String,
)