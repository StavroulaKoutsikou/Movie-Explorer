package com.example.movieexplorer.data.mapper

import com.example.movieexplorer.data.remote.dto.MovieDetailsDto
import com.example.movieexplorer.data.remote.dto.MovieDto
import com.example.movieexplorer.data.remote.dto.MoviesPageDto
import com.example.movieexplorer.domain.model.MovieDetails
import com.example.movieexplorer.domain.model.MoviesInfo
import com.example.movieexplorer.domain.model.MoviesPage

fun MovieDto.toDomain() = MoviesInfo(
    id = id,
    title = title,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    overview = overview
)

fun MoviesPageDto.toDomain() = MoviesPage(
    page = page,
    results = results.map { it.toDomain() },
    totalPages = totalPages
)

fun MovieDetailsDto.toDomain() = MovieDetails(
    id = id,
    title = title,
    overview = overview,
    genres = genres.map { it.name },
    runtime = runtime,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    releaseDate = releaseDate
)
