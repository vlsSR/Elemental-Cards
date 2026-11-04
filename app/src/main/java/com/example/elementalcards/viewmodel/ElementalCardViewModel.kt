package com.example.elementalcards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elementalcards.data.local.Card
import com.example.elementalcards.data.repository.CardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _userFirePoints = MutableStateFlow(0)
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
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}