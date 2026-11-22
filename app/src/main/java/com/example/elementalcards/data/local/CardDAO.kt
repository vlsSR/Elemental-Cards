package com.example.elementalcards.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDAO {

    @Query("SELECT * FROM cards ORDER BY id DESC")
    fun getDeck(): Flow<List<Card>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCard(card: Card): Long

    @Query("""
        UPDATE cards
        SET quantity = quantity + 1
        WHERE element = :element AND value = :value
    """)
    suspend fun increaseQuantity(element: Element, value: Int)

    suspend fun addCard(card: Card) {
        val insertedId = insertCard(card)

        if (insertedId == -1L) {
            increaseQuantity(card.element, card.value)
        }
    }

    @Delete
    suspend fun deleteCard(card: Card)

    @Query("DELETE FROM cards")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM cards")
    suspend fun getCardCount(): Int

    @Query("SELECT * FROM cards ORDER BY element, value")
    fun getUnlockedCards(): Flow<List<Card>>

    @Query("SELECT * FROM cards WHERE inDeck = 1")
    fun getDeckCards(): Flow<List<Card>>

    @Query("SELECT COUNT(*) FROM cards")
    suspend fun getUnlockedCount(): Int

    @Query("SELECT COUNT(*) FROM cards WHERE inDeck = 1")
    suspend fun getSelectedCount(): Int

    @Query("UPDATE cards SET inDeck = :selected WHERE id = :cardId")
    suspend fun setInDeck(cardId: Int, selected: Boolean)

}