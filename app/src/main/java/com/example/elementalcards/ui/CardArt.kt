package com.example.elementalcards.ui

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.elementalcards.R
import com.example.elementalcards.data.local.Card
import com.example.elementalcards.data.local.Element

private val fireArt = listOf(
    R.drawable.card_fire_1,
    R.drawable.card_fire_2,
    R.drawable.card_fire_3,
    R.drawable.card_fire_4,
    R.drawable.card_fire_5,
    R.drawable.card_fire_6,
    R.drawable.card_fire_7,
    R.drawable.card_fire_8,
    R.drawable.card_fire_9,
    R.drawable.card_fire_10,
)

private val waterArt = listOf(
    R.drawable.card_water_1,
    R.drawable.card_water_2,
    R.drawable.card_water_3,
    R.drawable.card_water_4,
    R.drawable.card_water_5,
    R.drawable.card_water_6,
    R.drawable.card_water_7,
    R.drawable.card_water_8,
    R.drawable.card_water_9,
    R.drawable.card_water_10,
)

private val iceArt = listOf(
    R.drawable.card_ice_1,
    R.drawable.card_ice_2,
    R.drawable.card_ice_3,
    R.drawable.card_ice_4,
    R.drawable.card_ice_5,
    R.drawable.card_ice_6,
    R.drawable.card_ice_7,
    R.drawable.card_ice_8,
    R.drawable.card_ice_9,
    R.drawable.card_ice_10,
)

@get:DrawableRes
val Card.artRes: Int
    get() = when (element) {
        Element.FIRE -> fireArt
        Element.WATER -> waterArt
        Element.ICE -> iceArt
    }[value - 1]

@Composable
fun Card.painter(): Painter = painterResource(artRes)