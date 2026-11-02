package com.example.elementalcards.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.elementalcards.viewmodel.ElementalCardViewModel

@Composable
fun BattleScreen(
    viewModel: ElementalCardViewModel,
    modifier: Modifier = Modifier
) {
    Text(text = "Battle Screen")
}