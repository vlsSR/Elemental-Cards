package com.example.elementalcards.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.elementalcards.R
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
    Column(modifier = Modifier.background(colorResource(R.color.black))) {
        //User and AI Points row
        Row(Modifier.weight(0.2f)) {
            Column(
                modifier = Modifier.weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Player:", fontSize = 16.sp, color = colorResource(R.color.white))
                Text(
                    "Fire: $userFirePoints",
                    fontSize = 12.sp,
                    color = colorResource(R.color.white)
                )
                Text(
                    "Water: $userWaterPoints",
                    fontSize = 12.sp,
                    color = colorResource(R.color.white)
                )
                Text("Ice: $userIcePoints", fontSize = 12.sp, color = colorResource(R.color.white))
            }
            Column(
                modifier = Modifier.weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("AI:", fontSize = 16.sp, color = colorResource(R.color.white))
                Text("Fire: $aiFirePoints", fontSize = 12.sp, color = colorResource(R.color.white))
                Text(
                    "Water: $aiWaterPoints",
                    fontSize = 12.sp,
                    color = colorResource(R.color.white)
                )
                Text("Ice: $aiIcePoints", fontSize = 12.sp, color = colorResource(R.color.white))
            }

        }

        //Current cards fighting row
        Row(
            Modifier.weight(0.6f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val emptyCard = Card(100, Element.MISSING, 1)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "PLAYER",
                    fontSize = 32.sp,
                    color = colorResource(R.color.light_blue),
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = emptyCard.painter(),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp),
                )
            }


            Text(
                "VS",
                fontSize = 32.sp,
                color = colorResource(R.color.white),
                textAlign = TextAlign.Center
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "AI",
                    fontSize = 32.sp,
                    color = colorResource(R.color.light_orange),
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = emptyCard.painter(),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp),
                )
            }
        }

        //Deck of cards row
        Row(Modifier.weight(0.2f)) {
            LazyRow(
                modifier = modifier,
                contentPadding = PaddingValues(all = 4.dp)

            ) {
                items(
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