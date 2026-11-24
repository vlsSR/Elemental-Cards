package com.example.elementalcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.elementalcards.ui.theme.ElementalCardsTheme
import com.example.elementalcards.view.StartApp
import com.example.elementalcards.viewmodel.BattleViewModel
import com.example.elementalcards.viewmodel.BattleViewModelFactory
import com.example.elementalcards.viewmodel.CollectionViewModel
import com.example.elementalcards.viewmodel.CollectionViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElementalCardsTheme {
                val battleViewModel: BattleViewModel = viewModel(factory = BattleViewModelFactory)
                val collectionViewModel: CollectionViewModel = viewModel(factory = CollectionViewModelFactory)
                StartApp(battleViewModel, collectionViewModel)
            }
        }
    }
}

