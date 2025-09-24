package com.example.movieexplorer.data.repository

import com.example.movieexplorer.data.mapper.toDomain
import com.example.movieexplorer.data.remote.MoviesApi
import com.example.movieexplorer.domain.model.MovieDetails
import com.example.movieexplorer.domain.model.MoviesPage
import com.example.movieexplorer.domain.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val api: MoviesApi,
    private val apiKey: String
) : MoviesRepository {

    override suspend fun getPopularPaged(page: Int, language: String): MoviesPage {
        val dto = api.getPopularMovies(
            apiKey = apiKey,
            language = language,
            page = page
        )
        return dto.toDomain()
    }

    override suspend fun searchMoviesPaged(
        query: String,
        page: Int,
        language: String
    ): MoviesPage {
        val dto = api.searchMovies(
            query = query,
            apiKey = apiKey,
            language = language,
            page = page,
            includeAdult = false
        )
        return dto.toDomain()
    }

    override suspend fun getMovieDetails(id: Int, language: String): MovieDetails {
        val dto = api.getMovieDetails(
            id = id,
            apiKey = apiKey,
            language = language
        )
        return dto.toDomain()
    }
}
