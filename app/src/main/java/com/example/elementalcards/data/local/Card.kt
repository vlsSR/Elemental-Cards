package com.example.elementalcards.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class Card(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val element: Element,
    val value: Int,
    val quantity: Int = 1,
    val inDeck: Boolean = false
)

