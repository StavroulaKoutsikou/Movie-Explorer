package com.example.movieexplorer.domain.repository

import com.example.movieexplorer.domain.model.MovieDetails
import com.example.movieexplorer.domain.model.MoviesInfo
import com.example.movieexplorer.domain.model.MoviesPage

interface MoviesRepository {
    suspend fun getPopularPaged(page: Int, language: String): MoviesPage
    suspend fun searchMoviesPaged(query: String, page: Int,language: String): MoviesPage
    suspend fun getMovieDetails(id: Int, language: String): MovieDetails
}

