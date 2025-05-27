package br.unavet.appvetproject.ui.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    viewModel: GameViewModel = koinViewModel(),
    navigateToMainMenu: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                GameUiEffect.NavigateToMainMenu -> navigateToMainMenu()
                is GameUiEffect.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                }
            }
        }
    }

    if (uiState.isGameOver) {
        GameOverScreen(score = uiState.score, onBackToMenu = {
            viewModel.onEvent(GameUiEvent.OnFinishGame)
        })
    } else {
        val question = uiState.currentQuestion

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Jogo") })
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Pontuação: ${uiState.score}")

                if (question != null) {
                    Text(text = question.question)

                    OutlinedTextField(
                        value = uiState.userInput,
                        onValueChange = { viewModel.onEvent(GameUiEvent.OnAnswerChanged(it)) },
                        label = { Text("Resposta") }
                    )

                    uiState.isAnswerCorrect?.let { isCorrect ->
                        Text(
                            if (isCorrect) "Correto!" else "Incorreto!",
                            color = if (isCorrect) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.error
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { viewModel.onEvent(GameUiEvent.OnSubmitAnswer) }) {
                            Text("Responder")
                        }
                        Button(onClick = { viewModel.onEvent(GameUiEvent.OnNextQuestion) }) {
                            Text("Próxima")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GameOverScreen(
    score: Int,
    onBackToMenu: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Fim de Jogo!", style = MaterialTheme.typography.headlineLarge)
        Text("Sua pontuação: $score")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBackToMenu) {
            Text("Voltar ao Menu")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameOverScreenPreview() {
    GameOverScreen(score = 80, onBackToMenu = {})
}