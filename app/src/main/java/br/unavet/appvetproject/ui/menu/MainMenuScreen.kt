package br.unavet.appvetproject.ui.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    viewModel: MainMenuViewModel = koinViewModel(),
    navigateToGame: () -> Unit,
    navigateToProfile: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                MainMenuUiEffect.NavigateToGame -> navigateToGame()
                MainMenuUiEffect.NavigateToProfile -> navigateToProfile()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Menu Principal") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Bem-vindo, ${uiState.userName}")
            Text(text = "Pontos: ${uiState.totalPoints}")
            Text(text = "Jogos: ${uiState.totalGamesPlayed}")

            Button(onClick = { viewModel.onEvent(MainMenuUiEvent.OnPlayClicked) }) {
                Text("Jogar")
            }

            Button(onClick = { viewModel.onEvent(MainMenuUiEvent.OnProfileClicked) }) {
                Text("Perfil")
            }
        }
    }
}