package com.example.elementalcards.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.elementalcards.data.local.Card
import com.example.elementalcards.data.local.Element
import com.example.elementalcards.ui.painter
import com.example.elementalcards.viewmodel.ElementalCardViewModel
import com.example.elementalcards.ui.painter

@Composable
fun BattleScreen(
    viewModel: ElementalCardViewModel,
    modifier: Modifier = Modifier
) {

    val deck by viewModel.deck.collectAsStateWithLifecycle()
    val userFirePoints by viewModel.userFirePoints.collectAsStateWithLifecycle()
    val userWaterPoints by viewModel.userWaterPoints.collectAsStateWithLifecycle()
    val userIcePoints by viewModel.userIcePoints.collectAsStateWithLifecycle()
    val aiFirePoints by viewModel.aiFirePoints.collectAsStateWithLifecycle()
    val aiWaterPoints by viewModel.aiWaterPoints.collectAsStateWithLifecycle()
    val aiIcePoints by viewModel.aiIcePoints.collectAsStateWithLifecycle()



    //Main column of the view
    Column(modifier = modifier) {
        //User and AI Points row
        Row() {

        }

        //Current cards fighting row
        Row() {

        }

        //Deck of cards row
        Row() {
            LazyRow(
                modifier = modifier,
                contentPadding = PaddingValues(all = 4.dp)
                
            ) {
                items (
                    items = deck,
                    key = { it.id }
                ) { deck ->
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = deck.painter(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                }

            }
        }
    }


}