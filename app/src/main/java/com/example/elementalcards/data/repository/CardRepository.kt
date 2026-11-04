package com.example.elementalcards.data.repository

import com.example.elementalcards.data.local.Card
import kotlinx.coroutines.flow.Flow
import com.example.elementalcards.data.local.CardDAO
import com.example.elementalcards.data.local.Element

class CardRepository(private val dao: CardDAO) {
    val getDeck: Flow<List<Card>> = dao.getDeck()

    suspend fun insert(card: Card) {
        dao.insertCard(card)
    }

    suspend fun delete(card: Card) {
        dao.deleteCard(card)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }

    suspend fun initDeck() {
        insert(Card(element = Element.FIRE, value = 1))
        insert(Card(element = Element.FIRE, value = 2))
        insert(Card(element = Element.FIRE, value = 3))
        insert(Card(element = Element.FIRE, value = 4))
        insert(Card(element = Element.FIRE, value = 5))

    }
}