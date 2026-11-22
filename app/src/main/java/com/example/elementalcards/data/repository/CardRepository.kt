package com.example.elementalcards.data.repository

import com.example.elementalcards.data.local.Card
import kotlinx.coroutines.flow.Flow
import com.example.elementalcards.data.local.CardDAO
import com.example.elementalcards.data.local.Element
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlin.random.Random

class CardRepository(private val dao: CardDAO) {
    val DECK_SIZE = 15
    val getDeck: Flow<List<Card>> = dao.getDeck()

    suspend fun addCard(card: Card) {
        dao.addCard(card)
    }

    suspend fun delete(card: Card) {
        dao.deleteCard(card)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }

    suspend fun initDeck() {
        if (dao.getCardCount() == 0) {
            addCard(Card(element = Element.FIRE, value = 1, inDeck = true))
            addCard(Card(element = Element.FIRE, value = 2, inDeck = true))
            addCard(Card(element = Element.FIRE, value = 3, inDeck = true))
            addCard(Card(element = Element.FIRE, value = 4, inDeck = true))
            addCard(Card(element = Element.FIRE, value = 5, inDeck = true))
            addCard(Card(element = Element.WATER, value = 1, inDeck = true))
            addCard(Card(element = Element.WATER, value = 2, inDeck = true))
            addCard(Card(element = Element.WATER, value = 3, inDeck = true))
            addCard(Card(element = Element.WATER, value = 4, inDeck = true))
            addCard(Card(element = Element.WATER, value = 5, inDeck = true))
            addCard(Card(element = Element.ICE, value = 1, inDeck = true))
            addCard(Card(element = Element.ICE, value = 2, inDeck = true))
            addCard(Card(element = Element.ICE, value = 3, inDeck = true))
            addCard(Card(element = Element.ICE, value = 4, inDeck = true))
            addCard(Card(element = Element.ICE, value = 5, inDeck = true))
        }
    }

    suspend fun getPlayingDeck(): MutableList<Card> {
        val deck = dao.getDeckCards().first()
        val shuffledDeck = deck.toMutableList()
        shuffledDeck.shuffle()

        return shuffledDeck
    }

    fun getAiPlayingDeck(): MutableList<Card> {
        val aiDeck =mutableListOf(
            Card(element = Element.FIRE, value = 1),
            Card(element = Element.FIRE, value = 2),
            Card(element = Element.FIRE, value = 3),
            Card(element = Element.FIRE, value = 4),
            Card(element = Element.FIRE, value = 5),
            Card(element = Element.WATER, value = 1),
            Card(element = Element.WATER, value = 2),
            Card(element = Element.WATER, value = 3),
            Card(element = Element.WATER, value = 4),
            Card(element = Element.WATER, value = 5),
            Card(element = Element.ICE, value = 1),
            Card(element = Element.ICE, value = 2),
            Card(element = Element.ICE, value = 3),
            Card(element = Element.ICE, value = 4),
            Card(element = Element.ICE, value = 5)
        )
        aiDeck.shuffle()
        return aiDeck
    }
}