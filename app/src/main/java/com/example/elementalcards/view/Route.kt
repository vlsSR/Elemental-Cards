package com.example.elementalcards.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.elementalcards.viewmodel.ElementalCardViewModel


sealed class Route(val path: String, val label: String) {
    object Landing: Route(
        path = "landing",
        label = "Landing"
    )
    object Battle: Route(
        path = "battle",
        label = "Battle"
    )
    object Deck: Route(
        path = "deck",
        label = "Deck"
    )
    object Pack: Route(
        path = "pack",
        label = "Pack"
    )
}

@Composable
fun StartApp(viewModel: ElementalCardViewModel) {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Landing.path,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.Landing.path) {
                LandingScreen(viewModel = viewModel,
                    { viewModel.startNewGame()
                        navController.navigate(Route.Battle.path)
                    })
            }
            composable(Route.Battle.path) {
                BattleScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize(), { navController.popBackStack() })
            }
        }

    }
}

