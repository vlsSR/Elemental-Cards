package com.example.elementalcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.elementalcards.data.local.Card
import com.example.elementalcards.data.local.Element
import com.example.elementalcards.ui.painter
import com.example.elementalcards.ui.theme.ElementalCardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElementalCardsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
    CardView(
        card = Card(element = Element.FIRE, value = 1),
        modifier = Modifier.aspectRatio(7f/2f)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ElementalCardsTheme {
        Greeting("Android")
    }
}

@Composable
fun CardView(card: Card, modifier: Modifier = Modifier) {
    Image(
        painter = card.painter(),
        contentDescription = "${card.element} ${card.value}",
        modifier = modifier
    )
}