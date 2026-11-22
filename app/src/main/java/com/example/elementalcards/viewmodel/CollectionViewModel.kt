package com.example.elementalcards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.elementalcards.ElementalCardApp
import com.example.elementalcards.data.repository.CardRepository

class CollectionViewModel(private val repository: CardRepository) : ViewModel() {

}

val CollectionViewModelFactory = viewModelFactory {
    initializer {
        val app =
            this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ElementalCardApp
        CollectionViewModel(app.repository)
    }
}