package com.example.movieexplorer.presentation.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movieexplorer.domain.model.MoviesInfo
import androidx.compose.material3.OutlinedTextFieldDefaults

// ---------- Παλέτα ----------
private val PurpleDeep = Color(0xFF6A1B9A)
private val PurpleContainer = Color(0xFFE1BEE7)
private val ScreenBackground = Color(0xFFF3E5F5)
private val CardOrange = Color(0xFFFFE0B2)
private val SearchFieldBg = Color(0xFFFFFFFF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    vm: MoviesViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    var tab by rememberSaveable { mutableStateOf(0) }
    var query by rememberSaveable { mutableStateOf("") }
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) { vm.loadPopular() }

    Scaffold(
        containerColor = ScreenBackground,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Movie Explorer",
                        style = MaterialTheme.typography.headlineLarge,
                        color = PurpleDeep
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = PurpleContainer,
                    titleContentColor = PurpleDeep
                )
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TabRow(selectedTabIndex = tab) {
                Tab(
                    selected = tab == 0,
                    onClick = { tab = 0; vm.loadPopular() },
                    text = { Text("Popular") },

                )
                Tab(
                    selected = tab == 1,
                    onClick = { tab = 1 },
                    text = { Text("Search") }
                )
            }

            if (tab == 1) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        label = { Text("Search movies here...") },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SearchFieldBg,
                            unfocusedContainerColor = SearchFieldBg,
                            disabledContainerColor = SearchFieldBg
                        )
                    )
                    Button(
                        onClick = { vm.search(query) },
                        modifier = Modifier.height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PurpleDeep,
                            contentColor = Color.White
                        )
                    ) { Text("Go") }
                }
            }

            when (val s = state) {
                is MoviesUiState.Idle -> Text("Pick a tab to start")
                is MoviesUiState.Loading -> Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) { CircularProgressIndicator() }
                is MoviesUiState.Error -> Text(s.message, color = MaterialTheme.colorScheme.error)
                is MoviesUiState.Success -> MoviesList(s.movies, onMovieClick)
            }
        }
    }
}

@Composable
private fun MoviesList(
    items: List<MoviesInfo>,
    onMovieClick: (Int) -> Unit
) {
    if (items.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No results")
        }
        return
    }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items, key = { it.id }) { movie ->
            MovieCard(movie) { onMovieClick(movie.id) }
        }
    }
}

@Composable
private fun MovieCard(
    m: MoviesInfo,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = CardOrange
        )
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            val poster = m.posterUrl()
            if (poster != null) {
                Image(
                    painter = rememberAsyncImagePainter(poster),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
            } else {
                Box(
                    Modifier
                        .size(80.dp)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) { Text("No\nImage") }
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {

                Text(m.title, style = MaterialTheme.typography.titleMedium, color = PurpleDeep)
                Text("⭐ ${"%.1f".format(m.voteAverage)}   •   ${m.releaseDate ?: "-"}")
                Text(m.overview, maxLines = 3)
            }
        }
    }
}
