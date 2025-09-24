package com.example.movieexplorer.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieexplorer.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repo: MoviesRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val state = _state.asStateFlow()

    fun load(id: Int, lang: String = "en-US") {
        viewModelScope.launch {
            _state.value = MovieDetailsUiState.Loading
            try {
                val details = repo.getMovieDetails(id, lang)
                _state.value = MovieDetailsUiState.Success(details)
            } catch (e: Exception) {
                _state.value = MovieDetailsUiState.Error("Failed to load details")
            }
        }
    }
}
