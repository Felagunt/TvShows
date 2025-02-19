package com.example.tvapp.data.remote.mappers

import com.example.tvapp.data.database.TvShowEntity
import com.example.tvapp.data.remote.dto.Image
import com.example.tvapp.domain.models.TvShow

fun TvShow.toTvShowEntity(): TvShowEntity {
    return TvShowEntity(
        id = id,
        name = name,
        language = language,
        image = image,
        status = status,
    )
}

fun TvShowEntity.toTvShow(): TvShow {
    return TvShow(
        id = id,
        image = image,
        language = language,
        name = name,
        status = status,
    )
}

val str = "43434 4343"
val result = str.split(" ")