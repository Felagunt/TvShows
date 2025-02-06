package com.example.tvapp.di

import com.example.tvapp.data.remote.TvShowApi
import com.example.tvapp.data.repository.ShowsRepositoryImp
import com.example.tvapp.domain.repository.ShowsRepository
import com.example.tvapp.util.Constants.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTvShowApi(): TvShowApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvShowApi::class.java)
    }

    @Provides
    @Singleton
    fun provideShowsRepository(api: TvShowApi): ShowsRepository {
        return ShowsRepositoryImp(api)
    }

}