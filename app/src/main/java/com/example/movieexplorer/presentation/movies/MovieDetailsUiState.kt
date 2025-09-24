package com.example.movieexplorer.presentation.movies

import com.example.movieexplorer.domain.model.MovieDetails

sealed interface MovieDetailsUiState {
    data object Loading : MovieDetailsUiState
    data class Success(val data: MovieDetails) : MovieDetailsUiState
    data class Error(val message: String) : MovieDetailsUiState
}
