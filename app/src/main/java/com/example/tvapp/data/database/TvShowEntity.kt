package com.example.tvapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tvapp.data.remote.dto.Image

@Entity(tableName = "tvShowsFavorite")
class TvShowEntity (
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val language: String,
    val image: String,
    val status: String
)