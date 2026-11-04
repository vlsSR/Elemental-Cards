package com.example.elementalcards

import android.app.Application
import kotlin.getValue
import com.example.elementalcards.data.local.AppDatabase
import com.example.elementalcards.data.repository.CardRepository

class ElementalCardApplication : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }

    val repository by lazy { CardRepository(database.cardDao()) }

}