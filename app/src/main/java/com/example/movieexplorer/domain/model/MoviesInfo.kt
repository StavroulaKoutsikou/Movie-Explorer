package com.example.movieexplorer.domain.model

data class MoviesInfo(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val overview: String
) {

    fun posterUrl(size: String = "w500"): String? =
        posterPath?.let { "https://image.tmdb.org/t/p/$size$it" }
}
