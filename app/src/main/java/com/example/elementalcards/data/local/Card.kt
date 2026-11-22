package com.example.elementalcards.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "cards",
    indices = [
        Index(value = ["element", "value"], unique = true)
    ]
)
data class Card(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val element: Element,
    val value: Int,
    val quantity: Int = 1,
    val inDeck: Boolean = false
)

