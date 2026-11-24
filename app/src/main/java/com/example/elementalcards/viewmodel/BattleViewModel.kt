package com.example.elementalcards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.elementalcards.ElementalCardApp
import com.example.elementalcards.data.local.Card
import com.example.elementalcards.data.local.Element
import com.example.elementalcards.data.local.Element.FIRE
import com.example.elementalcards.data.local.Element.ICE
import com.example.elementalcards.data.local.Element.MISSING
import com.example.elementalcards.data.local.Element.WATER
import com.example.elementalcards.data.repository.CardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Collections.emptyList

class BattleViewModel(private val repository: CardRepository) : ViewModel() {



    private var _winner = MutableStateFlow("")
    val winner: StateFlow<String> = _winner.asStateFlow()

    private val _victoryReward = MutableStateFlow<Card?>(null)
    val victoryReward: StateFlow<Card?> = _victoryReward.asStateFlow()

    private val _gameAlive = MutableStateFlow(true)
    val gameAlive: StateFlow<Boolean> = _gameAlive.asStateFlow()

    private val _aiPlayingDeck = MutableStateFlow<MutableList<Card>>(emptyList())

    private val _playingHand = MutableStateFlow<List<Card>>(emptyList())
    val playingHand: StateFlow<List<Card>> = _playingHand.asStateFlow()

    private val _drawPile = MutableStateFlow<List<Card>>(emptyList())


    private val _playerCard = MutableStateFlow(Card(100, MISSING, 1))
    val playerCard: StateFlow<Card> = _playerCard.asStateFlow()

    private val _aiCard = MutableStateFlow(Card(100, MISSING, 1))

    val aiCard: StateFlow<Card> = _aiCard.asStateFlow()
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
                repository.initDeck()

                val playerDeck = repository.getPlayingDeck()
                _playingHand.value = playerDeck.take(5)
                _drawPile.value = playerDeck.drop(5)
                _aiPlayingDeck.value = repository.getAiPlayingDeck().toMutableList()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


    fun playCard(playerCard: Card) {
        if (!_gameAlive.value) return
        if (playerCard.element == MISSING) return
        if (_aiPlayingDeck.value.isEmpty()) return

        updateCard(playerCard)

        val aiCard = _aiPlayingDeck.value.removeAt(0)

        _playerCard.value = playerCard
        _aiCard.value = aiCard
        if (playerCard.element == aiCard.element) {
            when {
                playerCard.value > aiCard.value -> addPlayerPoint(playerCard.element)
                playerCard.value < aiCard.value -> addAiPoint(playerCard.element)
            }
        } else {
            if (playerBeats(playerCard.element, aiCard.element)) {
                addPlayerPoint(playerCard.element)
            } else {
                addAiPoint(aiCard.element)
            }
        }
        checkVictory()
    }

    fun addPlayerPoint(element: Element) {
        when (element) {
            FIRE -> _userFirePoints.value++
            WATER -> _userWaterPoints.value++
            ICE -> _userIcePoints.value++
            MISSING -> Unit
        }
    }

    fun addAiPoint(element: Element) {
        when (element) {
            FIRE -> _aiFirePoints.value++
            WATER -> _aiWaterPoints.value++
            ICE -> _aiIcePoints.value++
            MISSING -> Unit
        }
    }

    fun playerBeats(playerElement: Element, aiElement: Element): Boolean {
        return when (playerElement) {
            FIRE -> aiElement == ICE
            WATER -> aiElement == FIRE
            ICE -> aiElement == WATER
            MISSING -> false
        }
    }

    fun updateCard(card: Card) {
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

    fun checkVictory() {
        if (!_gameAlive.value) return

        if (
            _userIcePoints.value >= 3 || _userFirePoints.value >= 3 || _userWaterPoints.value >= 3 ||
            (_userIcePoints.value >= 1 && _userFirePoints.value >= 1 && _userWaterPoints.value >= 1)
            ) {
            _winner.value = "PLAYER"
            giveVictoryReward()
            _gameAlive.value = false
        } else if(
            _aiIcePoints.value >= 3 || _aiFirePoints.value >= 3 || _aiWaterPoints.value >= 3 ||
            (_aiIcePoints.value >= 1 && _aiFirePoints.value >= 1 && _aiWaterPoints.value >= 1)
        ) {
            _winner.value = "AI"
            _gameAlive.value = false
        } else if(_aiPlayingDeck.value.size == 0) {
            _winner.value = "DRAW"
            _gameAlive.value = false
        }
    }

    fun reset() {
        _userIcePoints.value = 0
        _userWaterPoints.value = 0
        _userFirePoints.value = 0
        _aiWaterPoints.value = 0
        _aiFirePoints.value = 0
        _aiIcePoints.value = 0
        _winner.value = ""
        _victoryReward.value = null
        _playerCard.value = Card(100, MISSING, 1)
        _aiCard.value = Card(100, MISSING, 1)
        _gameAlive.value = true
    }

    private fun giveVictoryReward() {
        viewModelScope.launch {
            val element = listOf(
                FIRE,
                WATER,
                ICE
            ).random()

            val value = (1..10).random()

            val reward = Card(
                element = element,
                value = value,
                inDeck = false
            )

            repository.addCard(reward)
            _victoryReward.value = reward
        }
    }

    fun startNewGame() {
        reset()
        initDeck()
    }
}

val BattleViewModelFactory = viewModelFactory {
    initializer {
        val app =
            this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ElementalCardApp
        BattleViewModel(app.repository)
    }
}

