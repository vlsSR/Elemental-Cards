package com.example.elementalcards.data.local

enum class Element {
    FIRE,
    WATER,
    ICE;

    fun beats(other: Element): Boolean {
        return when (this) {
            FIRE -> other == ICE
            WATER -> other == FIRE
            ICE -> other == WATER
        }
    }
}