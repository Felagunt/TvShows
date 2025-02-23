package com.example.tvapp.data.remote.mappers

import com.example.tvapp.data.remote.dto.episodes.EpisodesDtoItem
import com.example.tvapp.domain.models.Episode

fun EpisodesDtoItem.toEpisode(): Episode {
    return Episode(
        airdate = airdate,
        airstamp = airstamp,
        airtime = airtime,
        id = id,
        image = image.medium,
        name = name,
        number = number,
        rating = rating.average,
        runtime = runtime,
        season = season,
        summary = summary,
        type = type,
        url = url
    )
}