package com.example.elementalcards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.elementalcards.ElementalCardApp
import com.example.elementalcards.R
import com.example.elementalcards.data.local.Card
import com.example.elementalcards.data.local.Element
import com.example.elementalcards.data.repository.CardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

data class CollectionCard(
    val card: Card,
    val isUnlocked: Boolean
)

data class CollectionUiState(
    val cardsByElement: Map<Element, List<CollectionCard>> = emptyMap(),
    val collectionCount: Int = 0,
    val equippedCount: Int = 0,
    val equippedPoints: Int = 0,
    val selectionMessage: Int? = null
)

class CollectionViewModel(private val repository: CardRepository) : ViewModel() {

    private val _state = MutableStateFlow(CollectionUiState())
    val state: StateFlow<CollectionUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initDeck()
            repository.unlockedCards.collect { unlockedCards ->
                val cardsByKey = unlockedCards.associateBy { it.element to it.value }
                val cardsByElement = listOf(Element.FIRE, Element.WATER, Element.ICE)
                    .associateWith { element ->
                        (1..10).map { value ->
                            val card = cardsByKey[element to value]
                            CollectionCard(
                                card = card ?: Card(element = element, value = value),
                                isUnlocked = card != null
                            )
                        }
                    }
                val equippedCards = unlockedCards.filter { it.inDeck }
                _state.value = CollectionUiState(
                    cardsByElement = cardsByElement,
                    collectionCount = unlockedCards.sumOf { it.quantity },
                    equippedCount = equippedCards.size,
                    equippedPoints = equippedCards.sumOf { it.value }
                )
            }
        }
    }

    fun toggleCard(collectionCard: CollectionCard) {
        if (!collectionCard.isUnlocked) return

        if (collectionCard.card.inDeck) {
            viewModelScope.launch {
                repository.setInDeck(collectionCard.card.id, false)
            }
            return
        }

        val equippedCards = _state.value.cardsByElement.values
            .flatten()
            .filter { it.card.inDeck }
        if (equippedCards.size >= 15) {
            _state.value = _state.value.copy(selectionMessage = R.string.collection_deck_full)
        } else if (
            equippedCards.sumOf { it.card.value } + collectionCard.card.value >
            repository.MAX_POINTS
        ) {
            _state.value =
                _state.value.copy(selectionMessage = R.string.collection_points_exceeded)
        } else {
            viewModelScope.launch {
                repository.setInDeck(collectionCard.card.id, true)
            }
        }
    }
}

val CollectionViewModelFactory = viewModelFactory {
    initializer {
        val app =
            this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ElementalCardApp
        CollectionViewModel(app.repository)
    }
}
