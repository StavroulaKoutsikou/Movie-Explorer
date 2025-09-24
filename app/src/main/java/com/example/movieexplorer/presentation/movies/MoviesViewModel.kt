package com.example.movieexplorer.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieexplorer.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repo: MoviesRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MoviesUiState>(MoviesUiState.Idle)
    val state = _state.asStateFlow()

    fun loadPopular(page: Int = 1, language: String = "en-US") {
        viewModelScope.launch {
            _state.value = MoviesUiState.Loading
            try {
                val result = repo.getPopularPaged(page, language)
                _state.value = MoviesUiState.Success(result.results)
            } catch (e: HttpException) {
                _state.value = MoviesUiState.Error("Server error (${e.code()})")
            } catch (e: IOException) {
                _state.value = MoviesUiState.Error("Check your internet connection")
            } catch (e: Exception) {
                _state.value = MoviesUiState.Error("Unexpected error")
            }
        }
    }

    fun search(query: String, page: Int = 1, language: String = "en-US") {
        if (query.isBlank()) {
            _state.value = MoviesUiState.Error("Type a movie title")
            return
        }
        viewModelScope.launch {
            _state.value = MoviesUiState.Loading
            try {
                val result = repo.searchMoviesPaged(query.trim(), page, language)
                _state.value = MoviesUiState.Success(result.results)
            } catch (e: HttpException) {
                _state.value = MoviesUiState.Error("Server error (${e.code()})")
            } catch (e: IOException) {
                _state.value = MoviesUiState.Error("Check your internet connection")
            } catch (e: Exception) {
                _state.value = MoviesUiState.Error("Unexpected error")
            }
        }
    }
}
