package com.example.elementalcards.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.elementalcards.R
import com.example.elementalcards.ui.painter
import com.example.elementalcards.viewmodel.BattleViewModel

@Composable
fun BattleScreen(
    viewModel: BattleViewModel,
    modifier: Modifier = Modifier,
    onEnding: () -> Unit
) {

    val deck by viewModel.playingHand.collectAsStateWithLifecycle()
    val userFirePoints by viewModel.userFirePoints.collectAsStateWithLifecycle()
    val userWaterPoints by viewModel.userWaterPoints.collectAsStateWithLifecycle()
    val userIcePoints by viewModel.userIcePoints.collectAsStateWithLifecycle()
    val aiFirePoints by viewModel.aiFirePoints.collectAsStateWithLifecycle()
    val aiWaterPoints by viewModel.aiWaterPoints.collectAsStateWithLifecycle()
    val aiIcePoints by viewModel.aiIcePoints.collectAsStateWithLifecycle()
    val playerCard by viewModel.playerCard.collectAsStateWithLifecycle()
    val computerCard by viewModel.aiCard.collectAsStateWithLifecycle()
    val gameAlive by viewModel.gameAlive.collectAsStateWithLifecycle()
    val winner by viewModel.winner.collectAsStateWithLifecycle()
    val reward by viewModel.victoryReward.collectAsStateWithLifecycle()

    //Main column of the view
    Column(modifier = Modifier.background(colorResource(R.color.black))) {
        //User and AI Points row
        Row(Modifier.weight(0.2f)) {
            Column(
                modifier = Modifier.weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.battle_player_text), fontSize = 16.sp, color = colorResource(R.color.white))
                Text(
                    stringResource(R.string.battle_element_fire, userFirePoints),
                    fontSize = 12.sp,
                    color = colorResource(R.color.white)
                )
                Text(
                    stringResource(R.string.battle_element_water, userWaterPoints),
                    fontSize = 12.sp,
                    color = colorResource(R.color.white)
                )
                Text(stringResource(R.string.battle_element_ice, userIcePoints), fontSize = 12.sp, color = colorResource(R.color.white))
            }
            Column(
                modifier = Modifier.weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                Text("AI:", fontSize = 16.sp, color = colorResource(R.color.white))
                Text(stringResource(R.string.battle_element_fire, aiFirePoints), fontSize = 12.sp, color = colorResource(R.color.white))
                Text(
                    stringResource(R.string.battle_element_water, aiWaterPoints),
                    fontSize = 12.sp,
                    color = colorResource(R.color.white)
                )
                Text(stringResource(R.string.battle_element_ice, aiIcePoints), fontSize = 12.sp, color = colorResource(R.color.white))
            }

        }

        //Current cards fighting row
        Row(
            Modifier.weight(0.6f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(bottom = 150.dp)) {
                Text(
                    stringResource(R.string.battle_player_text_caps),
                    fontSize = 32.sp,
                    color = colorResource(R.color.light_blue),
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = playerCard.painter(),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp),
                )
            }


            Text(
                stringResource(R.string.battle_vs_text_caps),
                fontSize = 32.sp,
                color = colorResource(R.color.white),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 150.dp)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(bottom = 150.dp)) {
                Text(
                    stringResource(R.string.battle_ai_text_caps),
                    fontSize = 32.sp,
                    color = colorResource(R.color.light_orange),
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = computerCard.painter(),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp),
                )
            }
        }

        //Deck of cards row
        Row(Modifier.weight(0.2f)) {
            LazyRow(
                modifier = modifier,
                contentPadding = PaddingValues(all = 4.dp),
                horizontalArrangement = Arrangement.Center

            ) {
                items(
                    items = deck,
                    key = { it.id }
                ) { deck ->
                        Image(
                            painter = deck.painter(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clickable { viewModel.playCard(deck) }
                        )
                }

            }
        }
    }

    if (!gameAlive) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(stringResource(R.string.battle_text_game_end)) },
            text = {
                Column(Modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(""+stringResource(R.string.battle_text_loser, winner)+
                            "${
                                if (winner.equals(stringResource(R.string.battle_player_text_caps))) stringResource(R.string.battle_text_if_win)
                                else ""
                            } ")

                    if (winner.equals(stringResource(R.string.battle_player_text_caps))) {
                        reward?.let { rewardCard ->
                            Image(
                                painter = rewardCard.painter(),
                                contentDescription = null,
                                modifier = Modifier.size(120.dp)
                            )
                        }

                    }
                }
            },
            confirmButton = {
                Button(onClick = { onEnding() }) {
                    Text(stringResource(R.string.battle_button_text_back))
                }
            }
        )
    }
}