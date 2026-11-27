package com.example.elementalcards.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.elementalcards.R
import com.example.elementalcards.viewmodel.BattleViewModel
import com.example.elementalcards.viewmodel.CollectionViewModel


sealed class Route(val path: String, val label: String) {
    object Landing: Route(
        path = "landing",
        label = "Landing"
    )
    object Battle: Route(
        path = "battle",
        label = "Battle"
    )
    object Collection: Route(
        path = "collection",
        label = "Collection"
    )
}

@Composable
fun StartApp(battleViewModel: BattleViewModel, collectionViewModel: CollectionViewModel) {
    val navController = rememberNavController()
    var showDeckWarning by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Landing.path,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.Landing.path) {
                LandingScreen(
                    onClickBattle = {
                        battleViewModel.startNewGame(
                            onDeckReady = {
                                navController.navigate(Route.Battle.path)
                            },
                            onInvalidDeck = { showDeckWarning = true }
                        )
                    },
                    onClickCollection = { navController.navigate(Route.Collection.path) }
                )
            }
            composable(Route.Battle.path) {
                BattleScreen(
                    viewModel = battleViewModel,
                    modifier = Modifier.fillMaxSize(),
                    onEnding = { navController.popBackStack() }
                )
            }
            composable(Route.Collection.path) {
                CollectionScreen(
                    viewModel = collectionViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }

    if (showDeckWarning) {
        AlertDialog(
            onDismissRequest = { showDeckWarning = false },
            title = { Text(stringResource(R.string.collection_invalid_deck_title)) },
            text = { Text(stringResource(R.string.collection_invalid_deck_message)) },
            confirmButton = {
                Button(onClick = {
                    showDeckWarning = false
                    navController.navigate(Route.Collection.path)
                }) {
                    Text(stringResource(R.string.button_colecction))
                }
            },
            dismissButton = {
                Button(onClick = { showDeckWarning = false }) {
                    Text(stringResource(R.string.collection_dismiss))
                }
            }
        )
    }
}

