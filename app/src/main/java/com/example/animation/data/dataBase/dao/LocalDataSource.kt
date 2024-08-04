package com.example.animation.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.animation.data.model.CardListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDataSource {

    @Query("SELECT * FROM CardItem")
    fun getCardItem(): Flow<List<CardListItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCardData(cardInfo: CardListItem)

}