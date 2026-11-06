package com.example.elementalcards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elementalcards.data.local.Card
import com.example.elementalcards.data.repository.CardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ElementalCardViewModel(private val repository: CardRepository) : ViewModel() {

    init {
        initDeck()
    }

    private val _playingHand = MutableStateFlow<List<Card>>(emptyList())
    val playingHand: StateFlow<List<Card>> = _playingHand.asStateFlow()

    private val _drawPile = MutableStateFlow<List<Card>>(emptyList())
    private var _userFirePoints = MutableStateFlow(0)
    val userFirePoints: StateFlow<Int> = _userFirePoints.asStateFlow()

    private val _userWaterPoints = MutableStateFlow(0)
    val userWaterPoints = _userWaterPoints.asStateFlow()

    private val _userIcePoints = MutableStateFlow(0)
    val userIcePoints = _userIcePoints.asStateFlow()

    private val _aiFirePoints = MutableStateFlow(0)
    val aiFirePoints = _aiFirePoints.asStateFlow()

    private val _aiWaterPoints = MutableStateFlow(0)
    val aiWaterPoints = _aiWaterPoints.asStateFlow()

    private val _aiIcePoints = MutableStateFlow(0)
    val aiIcePoints = _aiIcePoints.asStateFlow()

    private fun initDeck() {
        viewModelScope.launch {
            try {
                repository.deleteAll()
                repository.initDeck()
                val startingCards = repository.getPlayingDeck().toMutableSet()

                val initialHand = startingCards.take(5).toMutableList()
                val remainingCards = startingCards.drop(5).toMutableList()

                _playingHand.value = initialHand
                _drawPile.value = remainingCards


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun playCard(card: Card) {
        val currentHand = _playingHand.value.toMutableList()
        val currentDrawPile = _drawPile.value.toMutableList()
        val currentCardIndex = currentHand.indexOf(card)
        currentHand.remove(card)

        if (currentDrawPile.isNotEmpty()) {
            val nextCard = currentDrawPile.removeAt(0)
            currentHand.add(currentCardIndex, nextCard)
        }

        _playingHand.value = currentHand
        _drawPile.value = currentDrawPile
    }
}
