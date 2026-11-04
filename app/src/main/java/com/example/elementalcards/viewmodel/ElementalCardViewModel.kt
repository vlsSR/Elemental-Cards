package com.example.elementalcards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elementalcards.data.local.Card
import com.example.elementalcards.data.repository.CardRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ElementalCardViewModel(private val repository: CardRepository) : ViewModel() {

    init {
        initDeck()
    }

    val deck: StateFlow<List<Card>> = repository.getDeck
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private fun initDeck() {
        viewModelScope.launch {
            try {
                repository.deleteAll()
                repository.initDeck()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}