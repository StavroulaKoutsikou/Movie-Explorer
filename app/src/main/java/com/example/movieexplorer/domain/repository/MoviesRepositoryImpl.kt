package com.example.movieexplorer.domain.repository

import com.example.movieexplorer.data.mapper.toDomain
import com.example.movieexplorer.data.remote.dto.MovieDto
import com.example.movieexplorer.data.remote.dto.MoviesApi
import com.example.movieexplorer.data.remote.dto.MoviesPageDto
import com.example.movieexplorer.domain.model.MovieDetails
import com.example.movieexplorer.domain.model.MoviesInfo
import com.example.movieexplorer.domain.model.MoviesPage

class MoviesRepositoryImpl (
    private val api: MoviesApi,
    private val apiKey: String

): MoviesRepository{
    override suspend fun getPopularPaged(page: Int, language: String): MoviesPage {
        val dto = api.getPopularMovies(apiKey = apiKey, language = language, page = page)
        return dto.toDomain()
    }

    override suspend fun searchMoviesPaged(query: String, page: Int, language: String): MoviesPage {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetails(id: Int, language: String): MovieDetails {
        val dto = api.getMovieDetails(id = id, apiKey = apiKey, language = language)
        return dto.toDomain()
    }
}