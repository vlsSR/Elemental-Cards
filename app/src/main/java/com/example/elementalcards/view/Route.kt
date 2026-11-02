package com.example.elementalcards.view


sealed class Route(val path: String, val label: String) {
    object Landing: Route(
        path = "landing",
        label = "Landing"
    )
    object Battle: Route(
        path = "battle",
        label = "Battle"
    )
    object Deck: Route(
        path = "deck",
        label = "Deck"
    )
    object Pack: Route(
        path = "pack",
        label = "Pack"
    )
}

