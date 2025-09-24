package com.example.movieexplorer.presentation.movies

import com.example.movieexplorer.domain.model.MoviesInfo

sealed interface MoviesUiState {
    data object Idle : MoviesUiState
    data object Loading : MoviesUiState
    data class Success(val movies: List<MoviesInfo>) : MoviesUiState
    data class Error(val message: String) : MoviesUiState
}
