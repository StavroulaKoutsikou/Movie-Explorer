package com.example.movieexplorer.presentation.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movieexplorer.domain.model.MovieDetails

private val CardOrange = Color(0xFFFFE0B2)
private val PurpleDeep = Color(0xFF6A1B9A)
private val PurpleLight = Color(0xFFE1BEE7)

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    onBack: () -> Unit,
    vm: MovieDetailsViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(movieId) { vm.load(movieId) }

    Surface(color = CardOrange, modifier = Modifier.fillMaxSize()) {
        when (val s = state) {
            is MovieDetailsUiState.Loading ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

            is MovieDetailsUiState.Error ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(s.message, color = MaterialTheme.colorScheme.error)
                }

            is MovieDetailsUiState.Success -> DetailsContent(s.data, onBack)
        }
    }
}

@Composable
private fun DetailsContent(d: MovieDetails, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier.height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleDeep,
                    contentColor = Color.White
                )
            ) { Text("Back to The List") }
        }

        d.posterUrl()?.let { url ->
            Image(
                painter = rememberAsyncImagePainter(url),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PurpleLight),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = d.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = PurpleDeep,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "⭐ ${"%.1f".format(d.voteAverage)} • ${d.runtime ?: "-"}′ • ${d.releaseDate ?: "-"}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                if (d.genres.isNotEmpty()) {
                    Text(
                        text = d.genres.joinToString(" • "),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(text = d.overview)
            }
        }
    }
}
