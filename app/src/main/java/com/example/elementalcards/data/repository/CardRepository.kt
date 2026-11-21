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
        insert(Card(element = Element.WATER, value = 1))
        insert(Card(element = Element.WATER, value = 2))
        insert(Card(element = Element.WATER, value = 3))
        insert(Card(element = Element.WATER, value = 4))
        insert(Card(element = Element.WATER, value = 5))
        insert(Card(element = Element.ICE, value = 1))
        insert(Card(element = Element.ICE, value = 2))
        insert(Card(element = Element.ICE, value = 3))
        insert(Card(element = Element.ICE, value = 4))
        insert(Card(element = Element.ICE, value = 5))
    }

    suspend fun getPlayingDeck(): MutableSet<Card> {
        //Aqui uso lo de first pk si no se queda la corrutina en espera del Flow
        val totalDeck = getDeck.first().toMutableSet()
        val playingDeck = mutableSetOf<Card>()
        for(i in 1..15) {
            val cardNumber = Random.nextInt(0, totalDeck.size)
            playingDeck.add(totalDeck.elementAt(cardNumber))
            totalDeck.remove(totalDeck.elementAt(cardNumber))
        }
        return playingDeck
    }
}