package com.example.elementalcards.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elementalcards.R

@Preview(showBackground = true)
@Composable
fun LandingScreen() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.black))
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 150.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_elemental_cards),
            modifier = Modifier.size(200.dp),
            contentDescription = stringResource(R.string.app_name)
        )
        Text(
            stringResource(R.string.landing_title),
            modifier = Modifier.padding(top = 50.dp),
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.white)

        )
        Text(
            stringResource(R.string.landing_subtext),
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.white)

        )
        Button(
            onClick = { /* TODO: Handle button click */ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.orange)),
            modifier = Modifier
                .padding(horizontal = 80.dp)
                .padding(top = 32.dp)
                .fillMaxWidth()
        ) {
            Text(stringResource(R.string.button_play))
        }

        Button(
            onClick = { /* TODO: Handle button click */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.white),
                contentColor = colorResource(id = R.color.orange)
            ),
            modifier = Modifier
                .padding(horizontal = 80.dp)
                .padding(top = 3.dp)
                .fillMaxWidth()
        ) {
            Text(stringResource(R.string.button_colecction))
        }
    }
}