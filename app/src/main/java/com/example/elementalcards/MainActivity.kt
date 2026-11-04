package com.example.elementalcards

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.elementalcards.data.local.Card
import com.example.elementalcards.data.local.Element
import com.example.elementalcards.ui.painter
import com.example.elementalcards.ui.theme.ElementalCardsTheme
import com.example.elementalcards.view.BattleScreen
import com.example.elementalcards.view.LandingScreen
import com.example.elementalcards.view.Route
import com.example.elementalcards.viewmodel.ElementalCardViewModel
import com.example.elementalcards.viewmodel.ElementalCardViewModelFactory

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ViewModelConstructorInComposable")
    @OptIn(ExperimentalMaterial3Api::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as ElementalCardApplication

        val factory = ElementalCardViewModelFactory(app.repository)



        setContent {
            ElementalCardsTheme {
                val navController = rememberNavController()
                val viewModel: ElementalCardViewModel = viewModel(factory = factory)


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.Landing.path,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Route.Landing.path) {
                            LandingScreen(viewModel = viewModel, navController = navController)
                        }
                        composable(Route.Battle.path) {
                            BattleScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
                        }
                    }

                }
            }
        }
    }
}

