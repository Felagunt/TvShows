package com.example.tvapp.data.remote.mappers

import com.example.tvapp.data.database.TvShowDetailEntity
import com.example.tvapp.domain.models.TvShowDetail

fun TvShowDetail.ToTvShowEntity(): TvShowDetailEntity {
    return TvShowDetailEntity(
        id = id,
        ended = ended,
        imdb = imdb,
        thetvdb = thetvdb,
        tvrage = tvrage,
        genres = genres,
        name = name,
        image = image,
        originalImg = originalImg,
        language = language,
        officialSite = officialSite,
        premiered = premiered,
        rating = rating,
        runtime = runtime,
        status = status,
        summary = summary,
        type = type,
        url = url,
    )
}