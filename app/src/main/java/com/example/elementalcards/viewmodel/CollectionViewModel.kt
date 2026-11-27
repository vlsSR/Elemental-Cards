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
import kotlinx.coroutines.launch

data class CollectionCard(
    val card: Card,
    val isUnlocked: Boolean
)

data class ElementCards(
    val element: Element,
    val cards: List<CollectionCard>
)

data class CollectionUiState(
    val cardsByElement: List<ElementCards> = emptyList(),
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
                val cardsByElement = createCardsByElement(unlockedCards)
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

    private fun createCardsByElement(unlockedCards: List<Card>): List<ElementCards> {
        val result = mutableListOf<ElementCards>()

        for (element in listOf(Element.FIRE, Element.WATER, Element.ICE)) {
            val cards = mutableListOf<CollectionCard>()

            for (value in 1..10) {
                val card = unlockedCards.firstOrNull {
                    it.element == element && it.value == value
                }

                if (card == null) {
                    cards.add(
                        CollectionCard(
                            card = Card(element = element, value = value),
                            isUnlocked = false
                        )
                    )
                } else {
                    cards.add(CollectionCard(card = card, isUnlocked = true))
                }
            }

            result.add(ElementCards(element = element, cards = cards))
        }

        return result
    }

    fun toggleCard(collectionCard: CollectionCard) {
        if (!collectionCard.isUnlocked) return

        if (collectionCard.card.inDeck) {
            viewModelScope.launch {
                repository.setInDeck(collectionCard.card.id, false)
            }
            return
        }

        val equippedCards = _state.value.cardsByElement
            .flatMap { it.cards }
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
