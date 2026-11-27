package com.example.elementalcards.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.elementalcards.R
import com.example.elementalcards.data.local.Element
import com.example.elementalcards.ui.painter
import com.example.elementalcards.viewmodel.CollectionCard
import com.example.elementalcards.viewmodel.CollectionViewModel

@Composable
fun CollectionScreen(viewModel: CollectionViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val elements = listOf(Element.FIRE, Element.WATER, Element.ICE)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.black))
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.collection_title),
            color = colorResource(R.color.white),
            fontSize = 24.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                stringResource(R.string.collection_count, state.collectionCount),
                color = colorResource(R.color.white),
                textAlign = TextAlign.Center
            )
            Text(
                stringResource(R.string.collection_equipped_count, state.equippedCount),
                color = colorResource(R.color.white),
                textAlign = TextAlign.Center
            )
            Text(
                stringResource(R.string.collection_equipped_points, state.equippedPoints),
                color = colorResource(R.color.white),
                textAlign = TextAlign.Center
            )
        }

        if (state.collectionCount == 0) {
            Text(
                stringResource(R.string.collection_empty),
                color = colorResource(R.color.white),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        state.selectionMessage?.let {
            Text(
                stringResource(it),
                color = colorResource(R.color.light_orange),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            elements.forEach { element ->
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val heading = when (element) {
                        Element.FIRE -> R.string.collection_fire
                        Element.WATER -> R.string.collection_water
                        Element.ICE -> R.string.collection_ice
                        Element.MISSING -> R.string.collection_ice
                    }
                    Text(
                        stringResource(heading),
                        color = elementColor(element),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        items(
                            items = state.cardsByElement[element].orEmpty(),
                            key = { "${it.card.element}_${it.card.value}" }
                        ) { collectionCard ->
                            CollectionCardItem(
                                collectionCard = collectionCard,
                                onClick = { viewModel.toggleCard(collectionCard) }
                            )
                        }
                    }
                }
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(stringResource(R.string.collection_back))
        }
    }
}

@Composable
private fun CollectionCardItem(collectionCard: CollectionCard, onClick: () -> Unit) {
    val card = collectionCard.card
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .height(132.dp)
            .clickable(enabled = collectionCard.isUnlocked, onClick = onClick)
            .then(
                if (card.inDeck) {
                    Modifier.border(3.dp, elementColor(card.element), RoundedCornerShape(6.dp))
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = card.painter(),
            contentDescription = stringResource(
                R.string.collection_card_description,
                stringResource(
                    when (card.element) {
                        Element.FIRE -> R.string.collection_fire
                        Element.WATER -> R.string.collection_water
                        Element.ICE, Element.MISSING -> R.string.collection_ice
                    }
                ),
                card.value
            ),
            modifier = Modifier.fillMaxSize(),
            colorFilter = if (collectionCard.isUnlocked) null else {
                ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
            }
        )
        if (collectionCard.isUnlocked) {
            Text(
                stringResource(R.string.collection_quantity, card.quantity),
                color = colorResource(R.color.white),
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(colorResource(R.color.black).copy(alpha = 0.8f))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
private fun elementColor(element: Element) = colorResource(
    when (element) {
        Element.FIRE -> R.color.orange
        Element.WATER -> R.color.light_blue
        Element.ICE, Element.MISSING -> R.color.white
    }
)
