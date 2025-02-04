package com.example.tvapp.data.remote.mappers

import com.example.tvapp.data.remote.dto.details.TvShowDetailDto
import com.example.tvapp.domain.models.TvShowDetail

fun TvShowDetailDto.toTvShowDetail(): TvShowDetail {
    return TvShowDetail(
        id = id,
        name = name,
        language = language,
        status = status,
        premiered = premiered,
        ended = ended,
        runtime = runtime,
        genres = genres,
        summary = summary,
        rating = rating.average,
        tvrage = externals.tvrage,
        thetvdb = externals.thetvdb,
        imdb = externals.imdb,
        image = image.medium,
        originalImg = image.original,
        type = type,
        url = url,
        officialSite = officialSite,
    )
}