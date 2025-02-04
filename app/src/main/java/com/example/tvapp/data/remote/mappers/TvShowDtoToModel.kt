package com.example.tvapp.data.remote.mappers

import com.example.tvapp.data.remote.dto.TvShowDto
import com.example.tvapp.domain.models.TvShow


fun TvShowDto.toTvShow(): TvShow {
    return TvShow(
        id = id,
        image = image.medium,
        language = language,
        name = name,
        status = status
    )
}