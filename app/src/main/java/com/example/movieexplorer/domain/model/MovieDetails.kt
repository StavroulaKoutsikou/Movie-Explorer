package com.example.movieexplorer.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val genres: List<String>,
    val runtime: Int?,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val releaseDate: String?
) {
    fun posterUrl(size: String = "w500"): String? =
        posterPath?.let { "https://image.tmdb.org/t/p/$size$it" }
}
