package com.example.elementalcards.data.repository

import com.example.elementalcards.data.local.Card
import kotlinx.coroutines.flow.Flow
import com.example.elementalcards.data.local.CardDAO

class CardRepository(private val dao: CardDAO) {
    val getDeck: Flow<List<Card>> = dao.getDeck()

    suspend fun insert(card: Card) {
        dao.insertCard(card)
    }

    suspend fun delete(card: Card) {
        dao.deleteCard(card)
    }
}