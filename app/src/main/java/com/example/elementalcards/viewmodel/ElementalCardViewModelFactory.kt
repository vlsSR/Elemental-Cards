package com.example.elementalcards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.elementalcards.data.repository.CardRepository

class ElementalCardViewModelFactory (
    private val repository: CardRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ElementalCardViewModel::class.java)) {

            return ElementalCardViewModel(repository) as T

        }
        throw IllegalArgumentException("Clase ViewModel no reconocida por la fábrica: ${modelClass.name}")


    }
}