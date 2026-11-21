package com.example.elementalcards.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.elementalcards.data.local.Element.FIRE
import com.example.elementalcards.data.local.Element.ICE
import com.example.elementalcards.data.local.Element.MISSING
import com.example.elementalcards.data.local.Element.WATER

@Entity(tableName = "cards")
data class Card(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val element: Element,
    val value: Int,
    val quantity: Int = 1,
    val inDeck: Boolean = false

) {
    fun beats(other: Element): Boolean {
        return when (this.element) {
            FIRE -> other == ICE
            WATER -> other == FIRE
            ICE -> other == WATER
            MISSING -> false
        }
    }
}

