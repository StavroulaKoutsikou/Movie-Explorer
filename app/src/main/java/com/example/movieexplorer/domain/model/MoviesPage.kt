package com.example.movieexplorer.domain.model

data class MoviesPage (
    val page: Int,
    val results: List<MoviesInfo>,
    val totalPages: Int


)