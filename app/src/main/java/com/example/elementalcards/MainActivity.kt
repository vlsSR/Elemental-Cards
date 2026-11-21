package com.example.elementalcards

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.elementalcards.ui.theme.ElementalCardsTheme
import com.example.elementalcards.view.BattleScreen
import com.example.elementalcards.view.LandingScreen
import com.example.elementalcards.view.Route
import com.example.elementalcards.view.StartApp
import com.example.elementalcards.viewmodel.ElementalCardViewModel
import com.example.elementalcards.viewmodel.ElementalCardViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElementalCardsTheme {
                val viewModel: ElementalCardViewModel = viewModel(factory = ElementalCardViewModelFactory)
                StartApp(viewModel)
            }
        }
    }
}

