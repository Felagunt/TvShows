package com.example.tvapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [TvShowEntity::class]
)
abstract class FavoriteTvShowDatabase: RoomDatabase() {
    abstract val favoriteTvShowDao: FavoriteTvShowDao

    companion object {
        const val DB_NAME = "tvShow.db"
    }
}