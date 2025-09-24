package com.example.movieexplorer.di

import com.example.movieexplorer.BuildConfig
import com.example.movieexplorer.data.remote.MoviesApi
import com.example.movieexplorer.data.repository.MoviesRepositoryImpl
import com.example.movieexplorer.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides @Singleton
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides @Singleton
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi =
        retrofit.create(MoviesApi::class.java)

    @Provides @Singleton
    fun provideMoviesRepository(api: MoviesApi): MoviesRepository =
        MoviesRepositoryImpl(api, BuildConfig.TMDB_API_KEY)
}
